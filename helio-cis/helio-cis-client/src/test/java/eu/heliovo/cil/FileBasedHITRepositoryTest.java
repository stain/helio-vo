package eu.heliovo.cil;

import java.io.File;

import eu.heliovo.cil.FileBasedHITRepository;
import eu.heliovo.cil.HITRepository;
import eu.heliovo.cil.HITRepositoryException;

import junit.framework.TestCase;

public class FileBasedHITRepositoryTest extends TestCase 
{
	HITRepository repository = null;

	public void testAddUser() {
		try {
			removeDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			repository = new FileBasedHITRepository();
			assertFalse(repository.isUserPresent("user_a"));
			assertFalse(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));

			repository
					.addUser("user_a", "pwd_for_user_a", "profile_for_user_a");
			assertTrue(repository.isUserPresent("user_a"));
			assertFalse(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));

			repository
					.addUser("user_b", "pwd_for_user_b", "profile_for_user_a");
			assertTrue(repository.isUserPresent("user_a"));
			assertTrue(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));

			/*
			 * Re-create the repository
			 */
			repository = new FileBasedHITRepository();
			assertTrue(repository.isUserPresent("user_a"));
			assertTrue(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}
	}

	public void testGetUserProfileFor() {
		try {
			removeDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Adding user_a
		 */
		try {
			repository = new FileBasedHITRepository();
			repository
					.addUser("user_a", "pwd_for_user_a", "profile_for_user_a");
			assertTrue(repository.getUserProfileFor("user_a") != null);
//			assertTrue(repository.getUserProfileFor("user_a").equals(
//					"profile_for_user_a"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}
		/*
		 * Adding user_b
		 */
		try {
			repository
					.addUser("user_b", "pwd_for_user_b", "profile_for_user_b");
			assertTrue(repository.getUserProfileFor("user_b") != null);
//			assertTrue(repository.getUserProfileFor("user_b").equals(
//					"profile_for_user_b"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		/*
		 * Recreating the repository
		 */
		try {
			repository = new FileBasedHITRepository();

			assertTrue(repository.getUserProfileFor("user_a") != null);
//			assertTrue(repository.getUserProfileFor("user_a").equals(
//					"profile_for_user_a"));

			assertTrue(repository.getUserProfileFor("user_b") != null);
//			assertTrue(repository.getUserProfileFor("user_b").equals(
//					"profile_for_user_b"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}

	public void testIsUserPresent() {
		try {
			removeDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			HITRepository repository = new FileBasedHITRepository();

			assertFalse(repository.isUserPresent("user_a"));
			assertFalse(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));

			repository
					.addUser("user_a", "pwd_for_user_a", "profile_for_user_a");
			assertTrue(repository.isUserPresent("user_a"));
			assertFalse(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));

			repository
					.addUser("user_b", "pwd_for_user_b", "profile_for_user_a");
			assertTrue(repository.isUserPresent("user_a"));
			assertTrue(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}

		/*
		 * Recreating the repository
		 */
		try {
			HITRepository repository = new FileBasedHITRepository();
			assertTrue(repository.isUserPresent("user_a"));
			assertTrue(repository.isUserPresent("user_b"));
			assertFalse(repository.isUserPresent("no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}

	}

	public void testValidateUser() {
		try {
			removeDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			repository = new FileBasedHITRepository();

			assertFalse(repository.validateUser("user_a", "pwd_for_user_a"));
			assertFalse(repository.validateUser("user_a", "bad_pwd_for_user_a"));

			assertFalse(repository.validateUser("user_b", "pwd_for_user_b"));
			assertFalse(repository.validateUser("user_b", "bad_pwd_for_user_b"));

			assertFalse(repository.validateUser("no_such_user",
					"pwd_for_no_such_user"));
			assertFalse(repository.validateUser("no_such_user",
					"bad_pwd_for_no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}
		/*
		 * Adding user_a
		 */
		try {
			repository
					.addUser("user_a", "pwd_for_user_a", "profile_for_user_a");
			assertTrue(repository.validateUser("user_a", "pwd_for_user_a"));
			assertFalse(repository.validateUser("user_a", "bad_pwd_for_user_a"));

			assertFalse(repository.validateUser("user_b", "pwd_for_user_b"));
			assertFalse(repository.validateUser("user_b", "bad_pwd_for_user_b"));

			assertFalse(repository.validateUser("no_such_user",
					"pwd_for_no_such_user"));
			assertFalse(repository.validateUser("no_such_user",
					"bad_pwd_for_no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}
		/*
		 * Adding user_b
		 */
		try {
			repository
					.addUser("user_b", "pwd_for_user_b", "profile_for_user_b");
			assertTrue(repository.validateUser("user_a", "pwd_for_user_a"));
			assertFalse(repository.validateUser("user_a", "bad_pwd_for_user_a"));

			assertTrue(repository.validateUser("user_b", "pwd_for_user_b"));
			assertFalse(repository.validateUser("user_b", "bad_pwd_for_user_b"));

			assertFalse(repository.validateUser("no_such_user",
					"pwd_for_no_such_user"));
			assertFalse(repository.validateUser("no_such_user",
					"bad_pwd_for_no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			assertFalse(true);
		}

		/*
		 * Recreating the repository
		 */
		try {
			repository = new FileBasedHITRepository();
			assertTrue(repository.validateUser("user_a", "pwd_for_user_a"));
			assertFalse(repository.validateUser("user_a", "bad_pwd_for_user_a"));

			assertTrue(repository.validateUser("user_b", "pwd_for_user_b"));
			assertFalse(repository.validateUser("user_b", "bad_pwd_for_user_b"));

			assertFalse(repository.validateUser("no_such_user",
					"pwd_for_no_such_user"));
			assertFalse(repository.validateUser("no_such_user",
					"bad_pwd_for_no_such_user"));
		} catch (HITRepositoryException e) {
			e.printStackTrace();
			fail("Exception raised");
		}

	}

	private void removeDataFile() {
		String fileName = "HITRepository.data";
		// A File object to represent the filename
		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (f.exists()) {
			if (!f.canWrite())
				throw new IllegalArgumentException("Delete: write protected: "
						+ fileName);

			// If it is a directory, make sure it is empty
			if (f.isDirectory()) {
				String[] files = f.list();
				if (files.length > 0)
					throw new IllegalArgumentException(
							"Delete: directory not empty: " + fileName);
			}

			// Attempt to delete it
			boolean success = f.delete();

			if (!success)
				throw new IllegalArgumentException("Delete: deletion failed");
		}
	}
}
