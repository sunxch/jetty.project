//
//  ========================================================================
//  Copyright (c) 1995-2019 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package com.acme.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/asy/*", asyncSupported=true)
public class AsyncListenerServlet extends HttpServlet 
{
    public static class MyAsyncListener implements AsyncListener
    {
        @Resource(mappedName="maxAmount")
        private Double maxAmount;
        
        boolean postConstructCalled = false;
        boolean resourceInjected = false;
        
        @PostConstruct
        public void postConstruct()
        {
            postConstructCalled = true;
            resourceInjected = (maxAmount != null);
        }
        
        public boolean isPostConstructCalled()
        {
            return postConstructCalled;
        }
      
        public boolean isResourceInjected()
        {
            return resourceInjected;
        }

        @Override
        public void onComplete(AsyncEvent event) throws IOException
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onError(AsyncEvent event) throws IOException
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException
        {
            // TODO Auto-generated method stub
            
        }    
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException 
    {
        AsyncContext asyncContext = req.startAsync();
        MyAsyncListener listener = asyncContext.createListener(MyAsyncListener.class);

        PrintWriter writer = resp.getWriter();
        writer.println( "<html>");
        writer.println("<HEAD><link rel=\"stylesheet\" type=\"text/css\"  href=\"../stylesheet.css\"/></HEAD>");
        writer.println( "<body>");
        writer.println("<h1>AsyncListener</h2>");
        writer.println("<pre>");
        writer.println("<h2>@PostConstruct Callback</h2>");
        writer.println("<pre>");
        writer.println("@PostConstruct");
        writer.println("private void postConstruct ()");
        writer.println("{}"); 
        writer.println("</pre>");
        writer.println("<br/><b>Result: "+(listener.isPostConstructCalled()?"<span class=\"pass\">PASS</span>":"<span class=\"fail\">FAIL</span>")+"</b>");
        
        writer.println("<h2>@Resource Injection for env-entry </h2>");
        writer.println("<pre>");
        writer.println("@Resource(mappedName=\"maxAmount\")");
        writer.println("private Double maxAmount;");
        writer.println("</pre>");
        writer.println("<br/><b>Result: "+(listener.isResourceInjected()?" <span class=\"pass\">PASS</span>":" <span class=\"FAIL\">FAIL</span>")+"</b>");    
        
        writer.println( "</body>");
        writer.println( "</html>");
        writer.flush();
        writer.close();
        
        asyncContext.complete();
    }
}
