import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Account } from "../../../entities/account";
import { CurrentUserService } from "../../../services/current-user.service";

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
    isActive: boolean = false;
    showMenu: string = '';
    pushRightClass: string = 'push-right';
    public currentUser: Account;

    //Lista elementów menu z możliwymi dla nich rolami
    private allMenuItems: MenuItem[] = [
        {
            link: "/home",
            icon: "home",
            name: "Home",
            rolesAllowed: ["moderator", "editor", "candidate"]
        },
        {
            link: "/accounts",
            icon: "users",
            name: "Accounts",
            rolesAllowed: ["moderator"]
        },
        {
            link: "/positions",
            icon: "id-card",
            name: "Positions",
            rolesAllowed: ["moderator"]
        }
    ];
    public userMenuItems: MenuItem[];

    constructor(private currentUserService: CurrentUserService, private translate: TranslateService, public router: Router) {
        this.translate.addLangs(['en', 'fr', 'ur', 'es', 'it', 'fa', 'de']);
        this.translate.setDefaultLang('en');
        const browserLang = this.translate.getBrowserLang();
        this.translate.use(browserLang.match(/en|fr|ur|es|it|fa|de/) ? browserLang : 'en');
        this.userMenuItems = [];
        this.router.events.subscribe(val => {
            if (
                val instanceof NavigationEnd &&
                window.innerWidth <= 992 &&
                this.isToggled()
            ) {
                this.toggleSidebar();
            }
        });
    }

    ngOnInit() {
        this.currentUserService.getCurrentUser()
            .subscribe(x => this.setUserAndMenu(x));
    }

    setUserAndMenu(user: Account) {
        this.currentUser = user;
        this.getMenuItemsForRoles(user.roles);
    }

    getMenuItemsForRoles(roles: String[]) {
        for (let item of this.allMenuItems) {
            for (let role of roles) {
                if (item.rolesAllowed.indexOf(role.toLowerCase()) != -1) {
                    this.userMenuItems.push(item);
                    break;
                }
            }
        }
    }

    eventCalled() {
        this.isActive = !this.isActive;
    }

    addExpandClass(element: any) {
        if (element === this.showMenu) {
            this.showMenu = '0';
        } else {
            this.showMenu = element;
        }
    }

    isToggled(): boolean {
        const dom: Element = document.querySelector('body');
        return dom.classList.contains(this.pushRightClass);
    }

    toggleSidebar() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle(this.pushRightClass);
    }

    rltAndLtr() {
        const dom: any = document.querySelector('body');
        dom.classList.toggle('rtl');
    }

    changeLang(language: string) {
        this.translate.use(language);
    }

    onLoggedout() {
        localStorage.removeItem('isLoggedin');
    }
}

export class MenuItem {
    link: String;
    icon: String;
    name: String;
    rolesAllowed: String[];
}