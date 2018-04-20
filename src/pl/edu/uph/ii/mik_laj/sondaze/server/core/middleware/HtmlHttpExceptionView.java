package pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware;

import java.util.Map.Entry;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.StatusCode;

/**
 * Widok HTML dla warstawy posredniczacej obslugujacej wyjątki
 * 
 * @author andrzej
 *
 */
public class HtmlHttpExceptionView implements HttpExceptionViewInterface {

	public HtmlHttpExceptionView() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware.
	 * HttpExceptionViewInterface#getContent(pl.edu.uph.ii.mik_laj.sondaze.
	 * server.http.RequestInterface, java.lang.Exception)
	 */
	@Override
	public String getContent(RequestInterface request, Exception ex) {
		StringBuilder sb = new StringBuilder();
		printTitle(ex, sb);
		sb.append("<table>\n");
		sb.append("<tbody>\n");
		printRequestInfo(request, sb);
		printStackTrace(ex, sb);
		sb.append("</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

	void printTitle(Exception ex, StringBuilder sb) {
		if (ex instanceof HttpException) {
			HttpException httpEx = (HttpException) ex;
			sb.append("<h1>HTTP Exception: ")//
					.append(Integer.toString(httpEx.getStatusCode()))//
					.append(" ")//
					.append(StatusCode.getReasonPhrease(httpEx.getStatusCode()))//
					.append("</h1>\n");
		} else {
			sb.append("<h1> ")//
					.append(ex.getClass().getName())//
					.append(": ")//
					.append(ex.getMessage())//
					.append("</h1>\n");
		}
	}

	private void printRequestInfo(RequestInterface request, StringBuilder sb) {
		sb.append("\t<tr>\n");
		sb.append("\t\t<th>").append("Path").append("</th>\n");
		sb.append("\t\t<td>").append(request.getPath()).append("</td>\n");		
		sb.append("\t</tr>\n");

		sb.append("\t<tr>\n");
		sb.append("\t\t<th>").append("Path").append("</th>\n");
		sb.append("\t\t<td>").append(request.getMethod()).append("</td>\n");		
		sb.append("\t</tr>\n");

		sb.append("\t<tr>\n");
		sb.append("\t\t<th>").append("Headers").append("</th>\n");
		sb.append("\t\t<td>\n");
		sb.append("\t\t\t<ul>\n");
		for (Entry<String, String> header : request.getHeaders().entrySet()) {
			sb.append("\t\t\t\t<li>").append(header.getKey()).append(": ").append(header.getValue()).append("</li>\n");
		}
		sb.append("\t\t\t</ul>\n");
		sb.append("\t\t</td>\n");		
		sb.append("\t</tr>\n");
	}

	private void printStackTrace(Throwable throwable, StringBuilder sb) {
		sb.append("\t<tr>\n");
		sb.append("\t\t<th>").append("StackTrace").append("</th>\n");
		sb.append("\t\t<td>\n");
		sb.append("\t\t\t<ol>\n");
		for (StackTraceElement trace : throwable.getStackTrace()) {
			sb.append("\t\t\t\t<li>").append(trace.toString()).append("</li>\n");
		}
		sb.append("\t\t\t</ol>\n");
		sb.append("\t\t</td>\n");		
		sb.append("\t</tr>\n");

	}

	public String getContentType() {
		return "text/html";
	}
}
