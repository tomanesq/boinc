// This file is part of BOINC.
// http://boinc.berkeley.edu
// Copyright (C) 2010-2015 University of California
//
// BOINC is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version.
//
// BOINC is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// See the GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with BOINC.  If not, see <http://www.gnu.org/licenses/>.

#ifndef _BROWSERMAIN_WIN_H_
#define _BROWSERMAIN_WIN_H_


class CHTMLBrowserWnd;


class CBrowserModule : public ATL::CAtlExeModuleT<CBrowserModule>
{
public:
	DECLARE_LIBID(LIBID_HTMLGfxLib)

	CBrowserModule();

    static HRESULT InitializeCom() throw();
    static void UninitializeCom() throw();

	HRESULT PreMessageLoop(int nShowCmd) throw();
    HRESULT PostMessageLoop() throw();
    int BOINCParseCommandLine(int argc, char** argv);

    bool m_bFullscreen;
    CHTMLBrowserWnd* m_pWnd;
};


#endif