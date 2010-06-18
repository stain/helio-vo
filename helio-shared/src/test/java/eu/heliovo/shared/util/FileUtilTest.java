package eu.heliovo.shared.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.heliovo.shared.util.FileUtil.CopyOption;
import eu.heliovo.shared.util.FileUtil.OverwriteOption;

/**
 * Test the static methods of the doc framework utils ({@link FileUtil}).
 * The methods loadDir, loadFile, assertArgumentNotNull, assertHasText, and assertNotNull 
 * are trivial and will not be tested.  
 * @author marco.soldati (at fhnw.ch)
 *
 */
public class FileUtilTest {

    /**
     * A temp dir for testing.
     */
    private File tempSrc = null; 
    
    /**
     * A temp dir for testing.
     */
    private File tempTar = null; 
    
    /**
     * the temp dir.
     */
    private File tempDir = null;


    /**
     * set up the temp dir.
     */
    @Before
    public void setUp() throws Exception {
        tempSrc = FileUtil.loadDir(System.getProperty("java.io.tmpdir"), true);
        tempTar = new File(tempSrc, "helio_testtar");
        tempSrc = new File(tempSrc, "helio_testsrc");
        tempSrc.mkdir();
        tempTar.mkdir();
        
        // temp test dir 
        tempDir = FileUtil.loadDir(System.getProperty("java.io.tmpdir"), true);
        tempDir = new File(tempDir, "helio_classpath_test");
        remakeTemp();
    }

    /**
     * remove the temp dir
     */
    @After
    public void tearDown() throws Exception {
        FileUtil.removeDir(tempSrc);
        FileUtil.removeDir(tempTar);
    }
    
    
    
    /**
     * Sample text for the source test file.
     */
    private final static String SOURCE_TEXT = "this is the source test file";
    
    /**
     * Sample text for the target test file.
     */
    private final static String TARGET_TEXT = "this is the target test file";

    /**
     * Sample file name.
     */
    private static final String fileName = "overwrite.txt";
    
    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link OverwriteOption#ALWAYS}
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testCopyFileAlways() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);

        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);

        // target file must not exist.
        assertFalse(targetFile.exists());
        
        // copy file to dir
        int i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.ALWAYS, CopyOption.COPY_DIR);        
        assertEquals(1, i);        
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // overwrite target file
        createTestFile(targetFile, TARGET_TEXT);
        assertEquals(TARGET_TEXT, readFileContent(targetFile));
        
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.ALWAYS, CopyOption.COPY_DIR);        
        assertEquals(1, i);        
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // overwrite target file
        createTestFile(targetFile, TARGET_TEXT);

        // copy file to file
        i = FileUtil.copyFile(sourceFile, targetFile, OverwriteOption.ALWAYS, CopyOption.COPY_DIR);        
        assertEquals(1, i);        
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
    }
    
    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link OverwriteOption#NEVER}
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testCopyFileNever() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);

        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);

        // target file must not exist
        assertFalse(targetFile.exists());

        // copy file to dir
        int i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.NEVER, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // overwrite target
        createTestFile(targetFile, TARGET_TEXT);
        
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.NEVER, CopyOption.COPY_DIR);        
        assertEquals(0, i);        
        assertEquals(TARGET_TEXT, readFileContent(targetFile));

        // write file to file
        i = FileUtil.copyFile(sourceFile, targetFile, OverwriteOption.NEVER, CopyOption.COPY_DIR);        
        assertEquals(0, i);
        assertEquals(TARGET_TEXT, readFileContent(targetFile));        
    }

    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link OverwriteOption#IF_NEWER}
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testCopyFileIfNewer() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);

        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);

        // target file must not exist
        assertFalse(targetFile.exists());

        // copy file to dir
        int i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // overwrite target (set date to sometimes in the future)
        createTestFile(targetFile, TARGET_TEXT);
        targetFile.setLastModified(new Date().getTime() + 100000);
                
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);
        assertEquals(0, i);
        assertEquals(TARGET_TEXT, readFileContent(targetFile));
        
        // set time in the past
        targetFile.setLastModified(new Date().getTime() - 100000);
        
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        
        // set time to the same
        long time = new Date().getTime();
        sourceFile.setLastModified(time);
        targetFile.setLastModified(time);
                
        // copy file to file
        i = FileUtil.copyFile(sourceFile, targetFile, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);        
        assertEquals(0, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));        
    }
    
    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link OverwriteOption#IF_TIME_DIFFERS}
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testCopyFileIfTimeDiffers() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);

        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);

        // target file must not exist
        assertFalse(targetFile.exists());

        // copy file to dir
        int i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // overwrite target (set date to sometimes in the future)
        createTestFile(targetFile, TARGET_TEXT);
        targetFile.setLastModified(new Date().getTime() + 100000);
                
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));
        
        // set time in the past
        targetFile.setLastModified(new Date().getTime() - 100000);
        
        // copy file to dir
        i = FileUtil.copyFile(sourceFile, tempTar, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);
        assertEquals(1, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));        
        
        // set time to the same
        long time = new Date().getTime();
        sourceFile.setLastModified(time);
        targetFile.setLastModified(time);
                
        // copy file to file
        i = FileUtil.copyFile(sourceFile, targetFile, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);        
        assertEquals(0, i);
        assertEquals(SOURCE_TEXT, readFileContent(targetFile));        
    }

    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link CopyOption#COPY_CHILDREN}
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testCopyDirCopyChildren() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);
        
        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);
        
        // target file must not exist
        assertFalse(targetFile.exists());
        
        // copy dir to dir
        int i = FileUtil.copyFile(tempSrc, tempTar, OverwriteOption.ALWAYS, CopyOption.COPY_CHILDREN);
        assertEquals(1, i);
        File [] files = tempTar.listFiles();
        assertEquals(1, files.length);
        assertEquals(targetFile, files[0]);        
    }

    /**
     * Test {@link FileUtil#copyFile(File, File, OverwriteOption, CopyOption)} with {@link CopyOption#COPY_DIR}
     * @throws IOException if anything goes wrong 
     */
    @Test
    public void testCopyDir() throws IOException {        
        File sourceFile = new File(tempSrc, fileName);
        File targetFile = new File(tempTar, fileName);
        
        // create source file
        createTestFile(sourceFile, SOURCE_TEXT);
        
        // target file must not exist
        assertFalse(targetFile.exists());
        
        // copy dir to dir
        int i = FileUtil.copyFile(tempSrc, tempTar, OverwriteOption.ALWAYS, CopyOption.COPY_DIR);
        assertEquals(1, i);
        File [] files = tempTar.listFiles();
        assertEquals(1, files.length);
        assertEquals(new File(tempTar, tempSrc.getName()), files[0]);        
    }

    
    /**
     * Test extracting a single file from the file system.
     *
     */
    @Test
    public void testExtractFileSystemFile() {
        remakeTemp();
        String javaResourcePath = "/eu/heliovo/shared/util/test/resources/dummy.txt";        
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);
            String[] files = tempDir.list();
            assertEquals(1, files.length);
            assertEquals("dummy.txt", files[0]);
        } finally {
            deleteTemp();
        }
    }

    /**
     * Test extracting a directory from the file system.
     *
     */
    @Test
    public void testExtractFileSystemDir() {
        remakeTemp();
        String javaResourcePath = "/eu/heliovo/shared/util/test/resources";
        
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_CHILDREN);            
            String[] files = tempDir.list();
            assertTrue("Expected 1 file, but got " + Arrays.toString(files), files.length == 1);
            List<String> dataFiles = Arrays.asList(files);
            //System.out.println(dataFiles);
            assertTrue(dataFiles.contains("dummy.txt"));
        } finally {
            deleteTemp();
        }
    }

    /**
     * Test extracting a single file from the file system.
     *
     */
    @Test
    public void testExtractJarFile() {
        remakeTemp();
        
        String javaResourcePath = "/junit/framework/TestCase.class";
        
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_DIR);
            
            String[] files = tempDir.list();
            assertEquals(1, files.length);
            assertEquals("TestCase.class", files[0]);
        } finally {
            deleteTemp();
        }
    }
    
    /**
     * Test extracting a directory from the file system.
     *
     */
    @Test
    public void testExtractJarDir() {
        remakeTemp();        
        String javaResourcePath = "/junit/framework/";
        
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_TIME_DIFFERS, CopyOption.COPY_CHILDREN);
                        
            List<String> dataFiles = Arrays.asList(tempDir.list());
            assertTrue(dataFiles.size() > 2);
            assertTrue(dataFiles.contains("TestCase.class"));
            assertTrue(dataFiles.contains("Assert.class"));
        } finally {
            deleteTemp();
        }
    }

    /**
     * Test extracting a single file from the file system and only copy if the file has been
     * modified.
     *
     */
    @Test
    public void testExtractJarFileCompareTime() {
        remakeTemp();
        String javaResourcePath = "/junit/framework/TestCase.class";
        
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);
            
            String[] files = tempDir.list();
            assertEquals(1, files.length);
            assertEquals("TestCase.class", files[0]);
        } finally {
            deleteTemp();
        }
        
        remakeTemp();
        try {
            FileUtil.copyClasspathResource(javaResourcePath, tempDir, OverwriteOption.IF_NEWER, CopyOption.COPY_DIR);
            
            String[] files = tempDir.list();
            assertEquals(1, files.length);
            assertEquals("TestCase.class", files[0]);
        } finally {
            deleteTemp();
        }
        
    }
    
    /**
     * Test the relative path operation
     * @throws IOException if anything goes wrong
     */
    @Test
    public void testGetRelativePath() throws IOException {
        File testDir = new File(tempDir, "rel_path_test");
        testDir.mkdir();
        
        File homeDir = new File(testDir, "home");
        homeDir.mkdir();
        File targetDir = new File(testDir, "target");
        targetDir.mkdir();
        
        File homeFile = new File(homeDir, "home.txt");
        File targetFile = new File(targetDir, "target.txt");

        // create source and target file
        createTestFile(homeFile, SOURCE_TEXT);
        createTestFile(targetFile, SOURCE_TEXT);

        // test home dir - target dir
        String expected = ".." + File.separator + "target";
        assertEquals(expected, FileUtil.getRelativePath(homeDir, targetDir));
        
        // test home dir - target file
        expected = ".." + File.separator + "target" + File.separator + "target.txt";
        assertEquals(expected, FileUtil.getRelativePath(homeDir, targetFile));

        // test home file - target dir
        expected = ".." + File.separator + "target";
        assertEquals(expected, FileUtil.getRelativePath(homeFile, targetDir));

        // test home file - target file
        expected = ".." + File.separator + "target" + File.separator + "target.txt";
        assertEquals(expected, FileUtil.getRelativePath(homeFile, targetFile));
        
        // test base dir - target file
        expected = "target" + File.separator + "target.txt";
        assertEquals(expected, FileUtil.getRelativePath(testDir, targetFile));

        // test home dir - base dir
        expected = "..";
        assertEquals(expected, FileUtil.getRelativePath(homeDir, testDir));
        
        // test home dir - home dir
        expected = "";
        assertEquals(expected, FileUtil.getRelativePath(homeDir, homeDir));
        
        // test home dir - home file
        expected = "home.txt";
        assertEquals(expected, FileUtil.getRelativePath(homeDir, homeFile));
        
        FileUtil.removeDir(testDir);
    }

    /**
     * Delete temp dir.
     */
    private void deleteTemp() {
        if (tempDir.exists()) {
            int i = 0;
            while (!FileUtil.removeDir(tempDir)) { // do 3 retries as windows not always deletes the files immediately
                if (i < 3) {
                    try {                        
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                } else {
                    throw new RuntimeException("Unable to remove temp dir " + tempDir);
                }
                i++;
            }
        }
    }
    
    /**
     * Make the temp dir.
     *
     */
    private void remakeTemp() {
        deleteTemp();
        tempDir.mkdir();        
    }

    
    
    /**
     * Create a test file with some content.
     * @param file the file to create
     * @param content the content to write.
     * @throws IOException if anything goes wrong
     */
    private void createTestFile(File file, String content) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.append(content);
        fw.close();        
    }
    
    /**
     * Get the content of a file in a string
     * @param file the file to read
     * @return the content as string.
     * @throws IOException if anything goes wrong
     */
    private String readFileContent(File file) throws IOException {        
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }
}
