import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { routerTransition } from '../router.animations';
import { AuthenticationService } from '../shared/services/index';
import { Account } from '../entities/account';
import { CurrentUserService } from '../services/current-user.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    animations: [routerTransition()]
})
export class LoginComponent implements OnInit {
    email: string;
    password: string;
    alertType: string;
    alertMessage: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private currentUserService: CurrentUserService
    ) { }

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
            if (params.signup) {
                this.alertType = 'ok';
                this.alertMessage = 'Account registered successfully. You can now log in.';
            }
          });
        this.authenticationService.logout();
    }

    login() {
        this.authenticationService.login(this.email, this.password)
            .subscribe(
                response => {
                    this.currentUserService.setCurrentUser(response.body);
                    this.authenticationService.saveToken(response);
                    this.router.navigate(['/home']);
                },
                error => {
                    switch (error.status) {
                        case 403:
                            this.alertType = 'error';
                            this.alertMessage = 'Invalid email or password.';
                            // this.errorMessage = 'Invalid email or password.';
                            break;
                        case 500:
                            this.alertType = 'error';
                            this.alertMessage = 'Server error.';
                            // this.errorMessage = 'Server error.';
                            break;
                        default:
                            this.alertType = 'error';
                            this.alertMessage = 'Unexpected error occurred.';
                            // this.errorMessage = 'Unexpected error occurred.';
                    }
                }
            );
    }
}
