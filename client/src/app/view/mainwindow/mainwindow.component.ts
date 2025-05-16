import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthorizationManager} from "../../service/authorizationmanager";

@Component({
  selector: 'app-mainwindow',
  templateUrl: './mainwindow.component.html',
  styleUrls: ['./mainwindow.component.css']
})
export class MainwindowComponent {

  opened: boolean = true;

  menuGroup: any[] = [];
  repGroup:any[] = [
    {name:"Count BY Designation",routerlink:"reports/countbydesignation"},
  ];

  // Set Mat icons you need to add to Menus
  matIcons : any = {
    'Admin': 'person',
    'Sales': 'AttachMoney'
  };

  userImage: string = 'assets/default.png'
  constructor(private router: Router,public authService: AuthorizationManager) {
  }

  logout(): void {
    this.router.navigateByUrl("login")
    this.authService.clearUsername();
    this.authService.clearMenuState();
    localStorage.removeItem("Authorization");
    localStorage.removeItem("employee");
  }

  // Check that the logged user has the permission to view and then set Visible menu or else set not-visible menu
  isMenuVisible(category: string): boolean {
    let isVisible = true;

    this.menuGroup.forEach((menuGroup: { Menu: string; MenuItems: { name: string; isVisible: boolean }[] }) => {

      if (menuGroup.Menu === category) {
        isVisible = menuGroup.MenuItems.some(menuItem => menuItem.isVisible);
      }
    });

    return isVisible;
  }

  async ngOnInit(): Promise<void> {
    this.menuGroup = this.authService.getNavListItem();
    await this.authService.getAuth(this.authService.getUsername());
    this.userImage = this.authService.getUserProfile();
  }

}
