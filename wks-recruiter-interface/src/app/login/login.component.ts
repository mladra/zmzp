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
    errorMessage: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private currentUserService: CurrentUserService
    ) { }

    ngOnInit() {
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
                            this.errorMessage = 'Invalid email or password.';
                            break;
                        case 500:
                            this.errorMessage = 'Server error.';
                            break;
                        default:
                            this.errorMessage = 'Unexpected error occurred.';
                    }
                }
            );
    }
}
