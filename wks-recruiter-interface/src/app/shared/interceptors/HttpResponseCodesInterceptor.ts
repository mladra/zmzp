import 'rxjs/add/operator/do';
import { HttpInterceptor, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse, HttpRequest } from '@angular/common/http';
import { AuthenticationService } from '../services';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpResponseCodesInterceptor implements HttpInterceptor {

  constructor(
      private authorizationService: AuthenticationService,
      private router: Router
    ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).do((event: HttpEvent<any>) => {
      // do nothing
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
          // automatically redirects to login page
        if (err.status === 401 || err.status === 403) {
          this.authorizationService.logout();
          this.router.navigate(['/login']);
        }
      }
    });
  }
}
