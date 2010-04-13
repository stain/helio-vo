package eu.heliovo.dpas.ch;

import javax.swing.text.html.HTMLEditorKit;

/**
 * Helper class to make the Swing-internal HTML available to HELIO. This
 * class contains no real functionality.
 * 
 * @author Simon Felix
 */
@SuppressWarnings("serial")
public class HTMLParserFactory extends HTMLEditorKit
{
  private HTMLParserFactory()
  {
    
  }
  
  public static HTMLEditorKit.Parser createParser()
  {
    return new HTMLParserFactory().getParser();
  }
}
