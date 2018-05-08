import { Component, OnInit } from '@angular/core';
import { AlertsService } from "../services/alerts.service";
import { Account } from '../entities/account';
import { CurrentUserService } from '../services/current-user.service';

@Component({
    selector: 'app-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
    constructor(private currentUserService: CurrentUserService, private alertService: AlertsService) {
        this.alerts = this.alertService.getAlerts();
        this.myDocument = document;
    }
    public alerts: any[];
    private myDocument: any;
    ngOnInit() {
        this.myDocument.customContextMenu = {};
        this.myDocument = document;
        this.myDocument.keyupEventFunction = event => event.preventDefault();
        this.myDocument.printscrEventFunction = event => function (event) {
            if (event.keyCode == 44) {
                var sound: any = document.getElementById("error-audio");
                sound.play();
                var aux = document.createElement("input");
                aux.setAttribute("value", "Print Screen Prohibited!");
                document.body.appendChild(aux);
                aux.select();
                document.execCommand("copy");
                document.body.removeChild(aux);
                alert("Print Screen Prohibited!");
            }
        };
        this.myDocument.getSelectionText = function () {
            var text: any = "";
            var activeEl: any = document.activeElement;
            var activeElTagName: any = activeEl ? activeEl.tagName.toLowerCase() : null;
            if (
                (activeElTagName == "textarea") || (activeElTagName == "input" &&
                    /^(?:text|search|password|tel|url)$/i.test(activeEl.type)) &&
                (typeof activeEl.selectionStart == "number")
            ) {
                text = activeEl.value.slice(activeEl.selectionStart, activeEl.selectionEnd);
            } else if (window.getSelection) {
                text = window.getSelection().toString();
            }
            return text;
        };


        //=========context menu==========

        this.myDocument.customContextMenu.clickInsideElement = function (e, className) {
            var el = e.srcElement || e.target;

            if (el.classList.contains(className)) {
                return el;
            } else {
                while (el = el.parentNode) {
                    if (el.classList && el.classList.contains(className)) {
                        return el;
                    }
                }
            }

            return false;
        }

        this.myDocument.customContextMenu.getPosition = function (e) {
            var posx = 0;
            var posy = 0;

            if (!e) var e: any = window.event;

            if (e.pageX || e.pageY) {
                posx = e.pageX;
                posy = e.pageY;
            } else if (e.clientX || e.clientY) {
                posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
                posy = e.clientY + document.body.scrollTop + document.documentElement.scrollTop;
            }

            return {
                x: posx,
                y: posy
            }
        }

        this.myDocument.customContextMenu.contextMenuClassName = "context-menu";
        this.myDocument.customContextMenu.contextMenuItemClassName = "context-menu__item";
        this.myDocument.customContextMenu.contextMenuLinkClassName = "context-menu__link";
        this.myDocument.customContextMenu.contextMenuActive = "context-menu--active";
        this.myDocument.customContextMenu.clickCoords;
        this.myDocument.customContextMenu.clickCoordsX;
        this.myDocument.customContextMenu.clickCoordsY;
        this.myDocument.customContextMenu.menu = document.querySelector("#context-menu");
        this.myDocument.customContextMenu.menuItems = this.myDocument.customContextMenu.menu.querySelectorAll(".context-menu__item");
        this.myDocument.customContextMenu.menuState = 0;
        this.myDocument.customContextMenu.menuWidth;
        this.myDocument.customContextMenu.menuHeight;
        this.myDocument.customContextMenu.menuPosition;
        this.myDocument.customContextMenu.menuPositionX;
        this.myDocument.customContextMenu.menuPositionY;

        this.myDocument.customContextMenu.windowWidth;
        this.myDocument.customContextMenu.windowHeight;

        this.myDocument.customContextMenu.initCustomContextMenu = function () {
            var myDocument: any = document;
            myDocument.customContextMenu.contextListener();
            myDocument.customContextMenu.clickListener();
            myDocument.customContextMenu.keyupListener();
            myDocument.customContextMenu.resizeListener();
        }

        this.myDocument.customContextMenu.contextListener = function () {
            document.addEventListener("contextmenu", function (e) {
                e.preventDefault();
                var myDocument: any = document;
                myDocument.customContextMenu.toggleMenuOn();
                myDocument.customContextMenu.positionMenu(e);

            });
        }

        this.myDocument.customContextMenu.clickListener = function () {
            document.addEventListener("click", function (e) {
                var myDocument: any = document;
                var clickeElIsLink = myDocument.customContextMenu.clickInsideElement(e, myDocument.customContextMenu.contextMenuLinkClassName);
                if (clickeElIsLink) {
                    e.preventDefault();
                    myDocument.customContextMenu.menuItemListener(clickeElIsLink);
                } else {
                    var button = e.which || e.button;
                    if (button === 1) {
                        myDocument.customContextMenu.toggleMenuOff();
                    }
                }
            });
        }

        this.myDocument.customContextMenu.keyupListener = function () {
            window.onkeyup = function (e) {
                var myDocument: any = document;
                if (e.keyCode === 27) {
                    myDocument.customContextMenu.toggleMenuOff();
                }
            }
        }

        this.myDocument.customContextMenu.resizeListener = function () {
            window.onresize = function (e) {
                var myDocument: any = document;
                myDocument.customContextMenu.toggleMenuOff();
            };
        }

        this.myDocument.customContextMenu.toggleMenuOn = function () {
            var myDocument: any = document;
            if (myDocument.customContextMenu.menuState !== 1) {
                myDocument.customContextMenu.menuState = 1;
                myDocument.customContextMenu.menu.classList.add(myDocument.customContextMenu.contextMenuActive);
            }
        }

        this.myDocument.customContextMenu.toggleMenuOff = function () {
            var myDocument: any = document;
            if (myDocument.customContextMenu.menuState !== 0) {
                myDocument.customContextMenu.menuState = 0;
                myDocument.customContextMenu.menu.classList.remove(myDocument.customContextMenu.contextMenuActive);
            }
        }

        this.myDocument.customContextMenu.positionMenu = function (e) {
            var myDocument: any = document;
            var clickCoords: any = myDocument.customContextMenu.getPosition(e);
            myDocument.customContextMenu.clickCoordsX = clickCoords.x;
            myDocument.customContextMenu.clickCoordsY = clickCoords.y;

            myDocument.customContextMenu.menuWidth = myDocument.customContextMenu.menu.offsetWidth + 4;
            myDocument.customContextMenu.menuHeight = myDocument.customContextMenu.menu.offsetHeight + 4;

            myDocument.customContextMenu.windowWidth = window.innerWidth;
            myDocument.customContextMenu.windowHeight = window.innerHeight;

            if ((myDocument.customContextMenu.windowWidth - myDocument.customContextMenu.clickCoordsX) < myDocument.customContextMenu.menuWidth) {
                myDocument.customContextMenu.menu.style.left = myDocument.customContextMenu.windowWidth - myDocument.customContextMenu.menuWidth + "px";
            } else {
                myDocument.customContextMenu.menu.style.left = myDocument.customContextMenu.clickCoordsX + "px";
            }

            if ((myDocument.customContextMenu.windowHeight - myDocument.customContextMenu.clickCoordsY) < myDocument.customContextMenu.menuHeight) {
                myDocument.customContextMenu.menu.style.top = myDocument.customContextMenu.windowHeight - myDocument.customContextMenu.menuHeight + "px";
            } else {
                myDocument.customContextMenu.menu.style.top = myDocument.customContextMenu.clickCoordsY + "px";
            }
        }

        this.myDocument.customContextMenu.openLinkInNewTab = function (link) {
            var aux = document.createElement("a");
            aux.setAttribute("href", link);
            aux.setAttribute("target", "_blank");
            aux.click();
        };

        this.myDocument.customContextMenu.menuItemListener = function (link) {
            var myDocument: any = document;
            var myWindow: any = window;
            myDocument.customContextMenu.toggleMenuOff();
            var option: string = link.getAttribute("data-action");
            var selectedText: string = myDocument.getSelectionText();
            if (selectedText) {
                switch (option) {
                    case "wikipedia-pl":
                        myWindow.$.ajax({
                            url: '//pl.wikipedia.org/w/api.php',
                            data: {
                                action: 'query',
                                list: 'search',
                                srsearch: selectedText,
                                format: 'json',
                                formatversion: 2
                            },
                            dataType: 'jsonp',
                            success: function (x) {
                                if (x.query.search.length > 0) {
                                    myDocument.customContextMenu.openLinkInNewTab("http://pl.wikipedia.org/wiki/" + x.query.search[0].title);
                                } else {
                                    alert("No results found in Wikipedia PL...");
                                }
                            }
                        });
                        break;

                    case "wikipedia-en":
                        myWindow.$.ajax({
                            url: '//en.wikipedia.org/w/api.php',
                            data: {
                                action: 'query',
                                list: 'search',
                                srsearch: selectedText,
                                format: 'json',
                                formatversion: 2
                            },
                            dataType: 'jsonp',
                            success: function (x) {
                                if (x.query.search.length > 0) {
                                    myDocument.customContextMenu.openLinkInNewTab("http://en.wikipedia.org/wiki/" + x.query.search[0].title);
                                } else {
                                    alert("No results found in Wikipedia EN...");
                                }
                            }
                        });
                        break;

                    case "synonym-pl":
                        var output = "";
                        var key =  'ctbh3BuNErIIPskk081X';
                        myWindow.$.ajax({
                            url: "http://thesaurus.altervista.org/thesaurus/v1",
                            data: {
                                word: selectedText,
                                output: 'json',
                                language: 'pl_PL',
                                key: 'v6ZjxEqZm084SJrefClE'
                            },
                            dataType: 'json',
                            success: function(x) {
                                if (x.length != 0) {

                                    for (key in x.response) {
                                        output += x.response[key].list.synonyms + " ";
                                    }
                                    alert(output);
                                } else {
                                    alert("No synonyms found")
                                }
                            },
                            error: function() {
                                alert(output);
                            }
                        });
                        break;

                    case "synonym-en":
                        myWindow.$.ajax({
                            url: "http://thesaurus.altervista.org/thesaurus/v1",
                            data: {
                                word: selectedText,
                                output: 'json',
                                language: 'en_US',
                                key: 'ctbh3BuNErIIPskk081X'
                            },
                            dataType: 'json',
                            success: function(x) {
                                if (x.length != 0) {

                                    for (key in x.response) {
                                        output += x.response[key].list.synonyms + " ";
                                    }
                                    alert(output);
                                } else {
                                    alert("No synonyms found")
                                }
                            },
                            error: function() {
                                alert(output);
                            }
                        });
                        break;

                    default:
                        break;
                }
            } else {
                alert("Select text to search");
            }

        }

        this.currentUserService.getCurrentUser()
            .subscribe(x => this.makeKeyPermissons(x));
    }

    makeKeyPermissons(user: Account) {
        document.removeEventListener('keyup', this.myDocument.keyupEventFunction)
        if (user.roles.indexOf("Candidate") !== -1) {
            document.addEventListener('contextmenu', this.myDocument.keyupEventFunction);
            document.addEventListener('keyup', this.myDocument.printscrEventFunction(event));
        }
        if (user.roles.indexOf("Editor") !== -1) {
            var myDocument: any = document;
            myDocument.customContextMenu.initCustomContextMenu();
        }
    }
    closeAlert(alert: any) {
        this.alertService.closeAlert(alert);
    }

}
