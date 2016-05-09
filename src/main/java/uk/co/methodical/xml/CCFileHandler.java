package uk.co.methodical.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.co.methodical.database.MethodJDBCTemplate;

public class CCFileHandler extends DefaultHandler {
	
	private Integer stage = 0;
	private Integer numberOfHunts = 0;
	private String huntbellPath = "";
	private String symmetry = "";
	private Integer lengthOfLead = 0;
	private boolean inStage;
	private boolean inLengthOfLead;
	private boolean inNumberOfHunts;
	private boolean inHuntbellPath;
	private boolean inSymmetry;
	private String ccId;
	private boolean inName;
	private boolean inClassification;
	private boolean plain;
	private boolean inTitle;
	private boolean inNotation;
	private boolean inLeadHeadCode;
	private Integer methodSetId;
	private String notation;
	private String classification;
	private String title;
	private String leadHeadCode;
	private String name;
	private boolean little;
	
	private Integer methodCount = 0;

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		/*
		 * System.out.print("namespaceURI: " + namespaceURI);
		 * System.out.print(", localName: " + localName);
		 * System.out.println("qName: " + qName); 
		 * System.out.println(", atts: "
		 * + atts);
		 */
		
		if (qName.equals("methodSet")) {
			stage = 0;
			numberOfHunts = 0;
			huntbellPath = "";
			symmetry = "";
			lengthOfLead = 0;
		} else if (qName.equals("stage"))
			inStage = true;
		else if (qName.equals("lengthOfLead"))
			inLengthOfLead = true;
		else if (qName.equals("numberOfHunts"))
			inNumberOfHunts = true;
		else if (qName.equals("huntbellPath"))
			inHuntbellPath = true;
		else if (qName.equals("symmetry"))
			inSymmetry = true;

		else if (qName.equals("method")) {
			ccId = atts.getValue("id");
			
			// Re-initialise the method's attributes
			name = classification = title = notation = leadHeadCode = null;
			plain = little = false;
		}
		else if (qName.equals("name"))
			inName = true;
		else if (qName.equals("classification")) {
			inClassification = true;
			if (atts.getValue("plain") != null)
				plain = Boolean.parseBoolean(atts.getValue("plain"));
			if (atts.getValue("little") != null)
				little = Boolean.parseBoolean(atts.getValue("little"));
		}
		else if (qName.equals("title"))
			inTitle = true;
		else if (qName.equals("notation"))
			inNotation = true;
		else if (qName.equals("leadHeadCode"))
			inLeadHeadCode = true;
		
		super.startElement(namespaceURI, localName, qName, atts);
	
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		
		if (inStage)
			stage = Integer.parseInt(new String(ch, start, length));
		else if (inLengthOfLead)
			lengthOfLead = Integer.parseInt(new String(ch, start, length));
		else if (inNumberOfHunts)
			numberOfHunts = Integer.parseInt(new String(ch, start, length));
		else if (inHuntbellPath)
			huntbellPath = new String(ch, start, length);
		else if (inSymmetry)
			symmetry = new String(ch, start, length);
			
		else if (inName)
			name = new String(ch, start, length);
		else if (inClassification)
			classification = new String(ch, start, length);
		else if (inTitle)
			title = new String(ch, start, length);
		else if (inNotation)
			notation = new String(ch, start, length);
		else if (inLeadHeadCode)
			leadHeadCode = new String(ch, start, length);
			
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		//
		
		if (qName.equals("stage"))
			inStage = false;
		else if (qName.equals("lengthOfLead"))
			inLengthOfLead = false;
		else if (qName.equals("numberOfHunts"))
			inNumberOfHunts = false;
		else if (qName.equals("huntbellPath"))
			inHuntbellPath = false;
		else if (qName.equals("symmetry")) {
			inSymmetry = false;
			methodSetId = MethodJDBCTemplate.instance().addCCMethodSet(stage, lengthOfLead, numberOfHunts, huntbellPath, symmetry);
		}
		
		else if (qName.equals("name"))
			inName = false;
		else if (qName.equals("classification"))
			inClassification = false;
		else if (qName.equals("title"))
			inTitle = false;
		else if (qName.equals("notation"))
			inNotation = false;
		else if (qName.equals("leadHeadCode")) {
			inLeadHeadCode = false;
			MethodJDBCTemplate.instance().addCCMethod(ccId, name, classification, plain, little, title, notation, leadHeadCode, methodSetId);
			if (++methodCount % 100 == 0) 
				System.out.println("Loaded " + methodCount + " methods..");
		}
		
		super.endElement(uri, localName, qName);
	}
	
	
}
