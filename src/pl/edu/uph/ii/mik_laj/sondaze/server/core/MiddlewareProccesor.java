package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.Response;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.utils.Lists;

/**
 * Klasa przetwarzaca wszystkie warstwy posredniczace
 * @author andrzej
 *
 */
public class MiddlewareProccesor {

	/** 
	 * Lista funkcji posredniczacych
	 */
	private List<MiddlewareInterface> middlewares = new ArrayList<>();
	/**
	 * Ostatnia akcja posredniczaca. Zgodnie z specyfikacja uruchaia sie idealnie w polowie innych polecen
	 */
	private MiddlewareInterface defaultMiddleware;

	/**
	 * Konifigurje ostatia funkcje posredniczaca
	 * @param defaultMiddleware
	 */
	public void setDefaultMiddleware(MiddlewareInterface defaultMiddleware) {
		this.defaultMiddleware = defaultMiddleware;
	}

	/**
	 * Tworzy procesor funkcji posredniczacyhc
	 * 
	 */
	public MiddlewareProccesor() {

	}

	/**
	 * Wrapper na nastpena warstawe posredniczaca. 
	 * 
	 * @author andrzej
	 *
	 */
	public interface NextMiddleware {
		ResponseInterface procces(RequestInterface request, ResponseInterface response) throws Exception;
	}

	/**
	 * Dodacje kolejna funkcje posredniczacac
	 * @param middleware
	 */
	public void add(MiddlewareInterface middleware) {
		middlewares.add(middleware);
	}

	/**
	 * Przetwarza wszystkie warstwy posredniczace
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ResponseInterface procces(RequestInterface request, ResponseInterface response) throws Exception {
		NextMiddleware next = this.createWrappedMiddleware(defaultMiddleware, null);

		for (MiddlewareInterface middleware : Lists.reverse(middlewares)) {
			next = createWrappedMiddleware(middleware, next);
		}

		return next.procces(request, response);
	}

	/**
	 * Opakowuje warstawe posredniczaca. 
	 * @param middleware
	 * @param next
	 * @return
	 */
	private NextMiddleware createWrappedMiddleware(MiddlewareInterface middleware, NextMiddleware next) {
		return new NextMiddleware() {

			@Override
			public ResponseInterface procces(RequestInterface request, ResponseInterface response) throws Exception {
				return middleware.proccessMiddleware(request, response, next);
			}
		};
	}

	public static void main(String[] args) throws Exception {
		MiddlewareProccesor proccessor = new MiddlewareProccesor();
		proccessor.add(new MiddlewareInterface() {

			@Override
			public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
					NextMiddleware next) throws Exception {
				System.out.println("A1");
				ResponseInterface r = next.procces(request, response);
				System.out.println("B1");

				return r;
			}
		});
		proccessor.add(new MiddlewareInterface() {

			@Override
			public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
					NextMiddleware next) throws Exception {
				System.out.println("A2");
				ResponseInterface r = next.procces(request, response);
				System.out.println("B2");

				return r;
			}
		});
		proccessor.setDefaultMiddleware(new MiddlewareInterface() {

			@Override
			public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
					NextMiddleware next) {
				return response.setBody("AA");
			}
		});
		System.out.println(proccessor.procces(null, new Response()).getBody());

	}
}
