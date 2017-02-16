package com.meirengu.wxcs.servlet;

import java.io.IOException;

import com.meirengu.wxcs.command.JsSdkConfigureCommand;
import com.meirengu.wxcs.command.TripartiteServerUrlCommand;
import com.meirengu.wxcs.command.WebOauth2AccessTokenCommand;
import com.meirengu.wxcs.command.WebOauth2AuthorizeCommand;
import com.meirengu.wxcs.command.WebOauth2RefreshTokenCommand;
import com.meirengu.wxcs.command.WebOauth2UserTokenCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeiXinServiceServlet extends HttpServlet {

    /** */
    private static final long serialVersionUID = 718787909833594152L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String requestCommend = uri.substring(uri.lastIndexOf("/") + 1);
        if ("url".equals(requestCommend)) {
            new TripartiteServerUrlCommand(req, resp).doCommand();
        }else if("dtwxconfig".equals(requestCommend)){//old
            new JsSdkConfigureCommand(req, resp).doCommand();
        }else if("config".equals(requestCommend)){
            new JsSdkConfigureCommand(req, resp).doCommand();
        }else if ("oauth2_authorize".equals(requestCommend)) {
            new WebOauth2AuthorizeCommand(req, resp).doCommand();
        }else if ("oauth2_access_token".equals(requestCommend)) {
			new WebOauth2AccessTokenCommand(req, resp).doCommand();
		}else if ("oauth2_refresh_token".equals(requestCommend)) {
			new WebOauth2RefreshTokenCommand(req, resp).doCommand();
		}else if ("oauth2_userinfo".equals(requestCommend)) {
            new WebOauth2UserTokenCommand(req, resp).doCommand();
        }
    }
    

}
