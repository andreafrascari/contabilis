package eu.anastasis.tulliniHelpGest.helpDesk;

import javax.servlet.http.HttpServletRequest;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.TagNode;

import eu.anastasis.serena.exception.SerenaException;

public class HtmlParser extends eu.anastasis.serena.common.HtmlParser {

	@Override
	protected int doFilterText(ContentNode ct, TagNode parentTag)
			throws SerenaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int doFilterTagNode(HttpServletRequest request, TagNode tag)
			throws SerenaException {
		// TODO Auto-generated method stub
		return 0;
	}

}
