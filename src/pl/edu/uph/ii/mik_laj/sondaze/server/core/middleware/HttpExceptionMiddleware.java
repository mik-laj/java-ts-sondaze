package pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareProccesor.NextMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Warstwa posredniczacej obslugujacej wyjątki. Obsluguje odpowiedzi HTML i JSON.
 * 
 * @author andrzej
 *
 */
public class HttpExceptionMiddleware implements MiddlewareInterface {

	@Override
	public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
			NextMiddleware next) {
		try {
			return next.procces(request, response);
		} catch (Exception ex) {
			int statusCode = ex instanceof HttpException ? ((HttpException) ex).getStatusCode() : 501;

			HttpExceptionViewInterface view;
			if (request.hasHeader("Accept") && request.getHeader("Accept").contains("application/json")) {
				view = new JsonHttpExceptionView();
			} else {
				view = new HtmlHttpExceptionView();
			}
			return response.withStatus(statusCode)//
					.addHeader("Content-Type", view.getContentType()).setBody(view.getContent(request, ex));
		}
	}

}
