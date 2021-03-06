/*
 * This file is part of BOINC.
 * http://boinc.berkeley.edu
 * Copyright (C) 2012 University of California
 *
 * BOINC is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * BOINC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BOINC.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.berkeley.boinc.rpc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.util.Log;
import android.util.Xml;

import edu.berkeley.boinc.utils.Logging;

public class ProjectAttachReplyParser extends BaseParser {
    static final String PROJECT_ATTACH_REPLY_TAG = "project_attach_reply";
    static final String ERROR_NUM_TAG = "error_num";
    static final String MESSAGE_TAG = "message";

    private ProjectAttachReply mPAR;

    ProjectAttachReply getProjectAttachReply() {
        return mPAR;
    }

    public static ProjectAttachReply parse(String rpcResult) {
        try {
            ProjectAttachReplyParser parser = new ProjectAttachReplyParser();
            Xml.parse(rpcResult, parser);
            return parser.getProjectAttachReply();
        }
        catch(SAXException e) {
            return null;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(localName.equalsIgnoreCase(PROJECT_ATTACH_REPLY_TAG)) {
            mPAR = new ProjectAttachReply();
        }
        else {
            // Another element, hopefully primitive and not constructor
            // (although unknown constructor does not hurt, because there will be primitive start anyway)
            mElementStarted = true;
            mCurrentElement.setLength(0);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        try {
            if(mPAR != null && // we are inside <project_attach_reply>
               !localName.equalsIgnoreCase(PROJECT_ATTACH_REPLY_TAG)
                // Closing tag of <project_attach_reply> - nothing to do at the moment
                // inside it, so it should be ignored
            ) {
                // Not the closing tag - we decode possible inner tags
                trimEnd();
                if(localName.equalsIgnoreCase(ERROR_NUM_TAG)) {
                    mPAR.error_num = Integer.parseInt(mCurrentElement.toString());
                }
                else if(localName.equalsIgnoreCase(MESSAGE_TAG)) {
                    mPAR.messages.add(mCurrentElement.toString());
                }
            }
        }
        catch(NumberFormatException e) {
            if(Logging.ERROR.equals(Boolean.TRUE)) {
                Log.e(Logging.TAG, "ProjectAttachReplyParser.endElement error: ", e);
            }
        }
        mElementStarted = false;
    }
}
