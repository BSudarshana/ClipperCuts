import { Injectable } from '@angular/core';
import { AuthoritySevice } from './authoritysevice';
import {UserService} from "./userservice";
import {jwtDecode} from "jwt-decode";

@Injectable()
export class AuthorizationManager {

  public imageempurl!: string;

  private readonly localStorageUsreName = 'username';

  // please define all local storage suitable for relevant Main Menu name like below
  // Ex : Main Menu -> Report
  //  Then LocalStorage -> 'localstorageReportMenu'

  private readonly localStorageAdminMenus = 'admMenuState';
  private readonly localStorageAcademicMenus = 'acdMenuState';
  private readonly localStorageRegistrationMenus = 'regMenuState';
  private readonly localStorageClassMenus = 'clsMenuState';

  // Define Main Menu with there MenuItems
  Admin = [
    { name: 'Employee', isVisible: false, routerLink: 'employee' },
    { name: 'User', isVisible: false, routerLink: 'user' },
    { name: 'Privilege', isVisible: false, routerLink: 'privilege' },
    { name: 'Operations', isVisible: false, routerLink: 'operation' }
  ];



  // Return all the Main Menus
  getNavListItem(){
    return [
      { Menu : 'Admin' , MenuItems : this.Admin }
    ]
  }

  constructor(private us:UserService) {}

  // Check login user have permission to view Menus by setting ture to "isVisible"
  enableMenus(modules: { module: string; operation: string }[]): void {

    const menus = this.getNavListItem();

    // Set is Visible to 'true' by checking logged user authorities (check that the module is inside authority)
    menus.forEach(menuGroup => {
      menuGroup.MenuItems.forEach(menuItem => {
        menuItem.isVisible = modules.some(module => module.module.toLowerCase() === menuItem.name.toLowerCase());
      });
    });

    // Save Menus States in localstorage
    menus.forEach(menuGroup => {
      // @ts-ignore
      localStorage.setItem(this["localStorage" + menuGroup.Menu + "Menus"], JSON.stringify(menuGroup));
    });

  }

  async getAuth(username: string): Promise<void> {

    // Set Username for additional needs
    this.setUsername(username);

    try {
      // get Authorities to relevant logged user (Decoded JWT Token)
      const authoritiesArray = this.getAuthorities();


      // get Employee details by username
      const employee = await this.us.getEmployeeByUserName(username);

      //set employee for additional needs
      this.setEmployee(employee);
      // set user profile
      this.setUserProfile();

      if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
        // extract modules and authorities from previous authoritiesArray
        const authorities = this.extractAuthorities(authoritiesArray);
        this.enableMenus(authorities);
      } else {
        console.log('Authorities are undefined or not an array');
      }

    } catch (error) {
      console.error(error);
    }
  }

  // extract modules and authorities separately from JWT Token
  extractAuthorities(authoritiesArray: string[]): { module: string; operation: string }[] {
    return authoritiesArray.map(authority => {
      const [module, operation] = authority.split('-');
      return { module, operation };
    });
  }

  getUsername(): string {
    return localStorage.getItem(this.localStorageUsreName) || '';
  }

  setUsername(value: string): void {
    localStorage.setItem(this.localStorageUsreName, value);
  }

  setEmployee(employee: any): void {
    localStorage.setItem('employee', JSON.stringify(employee));
  }

  setUserProfile(): void {
    const employee = localStorage.getItem('employee');
    if (employee) {
      try {
        const img = JSON.parse(employee).photo;
        this.imageempurl = atob(img);
      } catch (error) {
        //console.error("Error decoding employee photo:", error);
        this.imageempurl = "assets/default.png";
      }
    }
  }

  // separate authorities (Modules and Authorities) from JWT Token
  getAuthorities(){
    // @ts-ignore
    const jwtToken = localStorage.getItem("Authorization").split(' ')[1];
    return jwtDecode(jwtToken).aud;
  }

  getUserProfile(): string {
    return this.imageempurl;
  }

  // Get all Menu States in localstorage storage to check what are visible again when refreshing
  initializeMenuState(): void {

    const menus = this.getNavListItem();

    menus.forEach(menuState => {
      // @ts-ignore
      const localStorageState = localStorage.getItem(this['localStorage' + menuState.Menu + 'Menus']);
      if (localStorageState) {
        menuState.Menu = JSON.parse(localStorageState);
      }
    });
  }

  clearUsername(): void {
    localStorage.removeItem(this.localStorageUsreName);
  }

  // Clear all menu states when user logout
  clearMenuState(): void {
    const menus = this.getNavListItem();
    menus.forEach(menu => {
      // @ts-ignore
      localStorage.removeItem(this['localStorage' + menu.Menu + 'Menus']);
    });
  }


}
