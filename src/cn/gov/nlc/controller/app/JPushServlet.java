package cn.gov.nlc.controller.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gov.nlc.util.Jdpush;

/**
 * Servlet implementation class JPushServlet
 */
@WebServlet("/JPushServlet")
public class JPushServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String appKey ="b4ad1a8033084bf44889739c";  
    private static final String masterSecret = "c14bfc4124a01aa1c33bb0ec";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JPushServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Jdpush.testSendPush(appKey,masterSecret);  
        System.out.println("sucess");  
      Jdpush.pushMessage(2, "wen_jin", "机器人创想2", "国家图书馆", "subject", "4aeafd24564946c30156a177fbe82988");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
