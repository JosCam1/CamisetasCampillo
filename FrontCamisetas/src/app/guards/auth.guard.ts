import { CanActivateFn, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from '../servicios/login.service';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<boolean | import('@angular/router').UrlTree> => {
  const loginService = inject(LoginService);
  const router = inject(Router);
  const expectedRole = route.data['expectedRole'];

  return loginService.fetchUsuario().pipe(
    map(usuario => {
      if (usuario && usuario.rol.nombre === expectedRole) {
        return true;
      } else {
        return router.createUrlTree(['/error']);
      }
    }),
    catchError(() => {
      return of(router.createUrlTree(['/error']));
    })
  );
  
};