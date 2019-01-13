package ru.rsoi.gateway.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.catalina.connector.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import ru.rsoi.gateway.response.ResponsePageImpl;
import ru.rsoi.models.DeliveryModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@WebFilter("/*")
public class TrackingFilter extends ZuulFilter {

   /* public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);

        String loggedInUser = "Unregistered user";

        //assuming you have a session attribute named user with the username
        if(session != null && session.getAttribute("user") != null) {
            loggedInUser = (String) session.getAttribute("user");
        }

        Date accessedDate = new Date();
        filterConfig.getServletContext().log(
                String.format("%s accessed context %s on %tF %tT",
                        loggedInUser, request.getRequestURI() ,
                        accessedDate, accessedDate)
        );
        System.out.print(String.format("%s accessed context %s on %tF %tT",
                loggedInUser, request.getRequestURI() ,
                accessedDate, accessedDate));

        filterChain.doFilter(servletRequest, servletResponse);
    }*/


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        long accessedDate = System.currentTimeMillis();
        if (ctx.getRequest().getHeader("token")!=null)
        { String login = ctx.getRequest().getHeader("token");
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8080/setTime?token="+login+"&date="+accessedDate);
            httpGet.addHeader("token", login);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                return null;
            }
            } catch (IOException e1) {
            e1.printStackTrace();
        }
         return null;
    }
        else return null;
    }


    }