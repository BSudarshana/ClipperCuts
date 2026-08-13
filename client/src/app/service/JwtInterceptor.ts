import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const storedToken = localStorage.getItem('Authorization');

    if (storedToken) {
      // Login responses may include the "Bearer " prefix. Always normalize
      // the stored value before constructing the request header.
      const rawToken = storedToken.replace(/^Bearer\s+/i, '').trim();

      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${rawToken}`
        }
      });
    }

    return next.handle(request);
  }
}
