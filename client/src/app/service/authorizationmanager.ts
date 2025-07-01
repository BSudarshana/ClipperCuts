import { Injectable } from '@angular/core';
import { AuthoritySevice } from './authoritysevice';
import {UserService} from "./userservice";
import {jwtDecode} from "jwt-decode";

@Injectable()
export class AuthorizationManager {

  // Holds the URL of the employee's profile image
  public imageempurl!: string;

  // LocalStorage key for storing the logged-in username
  private readonly localStorageUsreName = 'username';

  // LocalStorage keys for saving menu visibility states per main menu category
  private readonly localStorageAdminMenus = 'admMenuState';
  private readonly localStorageAcademicMenus = 'acdMenuState';
  private readonly localStorageRegistrationMenus = 'regMenuState';
  private readonly localStorageClassMenus = 'clsMenuState';
  private readonly localStorageSalesMenus = 'salesMenuState';

  // Admin menu items with visibility flags and routes
  Admin = [
    { name: 'Employee', isVisible: false, routerLink: 'employee' },
    { name: 'User', isVisible: false, routerLink: 'user' },
    { name: 'Privilege', isVisible: false, routerLink: 'privilege' },
    { name: 'Operations', isVisible: false, routerLink: 'operation' }
  ];

  // Sales menu items
  Sales = [
    { name: 'Customer', isVisible: false, routerLink: 'customer' }
  ];

  // Returns the list of all main menus with their corresponding items
  getNavListItem() {
    return [
      { Menu: 'Admin', MenuItems: this.Admin },
      { Menu: 'Sales', MenuItems: this.Sales }
    ];
  }

  // Injects the UserService for backend communication
  constructor(private us: UserService) {}

  // Enables menu items for the logged-in user based on their authorities
  enableMenus(modules: { module: string; operation: string }[]): void {
    const menus = this.getNavListItem();

    // Check each menu item against the user's permissions
    menus.forEach(menuGroup => {
      menuGroup.MenuItems.forEach(menuItem => {
        menuItem.isVisible = modules.some(
          module => module.module.toLowerCase() === menuItem.name.toLowerCase()
        );
      });
    });

    // Save the updated menu visibility states to localStorage
    menus.forEach(menuGroup => {
      // @ts-ignore
      localStorage.setItem(this["localStorage" + menuGroup.Menu + "Menus"], JSON.stringify(menuGroup));
    });
  }

  // Loads user information and sets up menus based on permissions
  async getAuth(username: string): Promise<void> {
    this.setUsername(username);

    try {
      // Extract authorities (permissions) from JWT token
      const authoritiesArray = this.getAuthorities();

      // Fetch employee details by username
      const employee = await this.us.getEmployeeByUserName(username);

      this.setEmployee(employee);
      this.setUserProfile();

      // If authorities are valid, process and enable menus
      if (authoritiesArray !== undefined && Array.isArray(authoritiesArray)) {
        const authorities = this.extractAuthorities(authoritiesArray);
        this.enableMenus(authorities);
      } else {
        console.log('Authorities are undefined or not an array');
      }

    } catch (error) {
      console.error(error);
    }
  }

  // Converts raw authorities array (e.g., "Admin-Read") into structured objects
  extractAuthorities(authoritiesArray: string[]): { module: string; operation: string }[] {
    return authoritiesArray.map(authority => {
      const [module, operation] = authority.split('-');
      return { module, operation };
    });
  }

  // Retrieves the stored username from localStorage
  getUsername(): string {
    return localStorage.getItem(this.localStorageUsreName) || '';
  }

  // Stores the logged-in username in localStorage
  setUsername(value: string): void {
    localStorage.setItem(this.localStorageUsreName, value);
  }

  // Stores employee object (used for profile and other operations)
  setEmployee(employee: any): void {
    localStorage.setItem('employee', JSON.stringify(employee));
  }

  // Decodes and sets the user profile image from localStorage
  setUserProfile(): void {
    const employee = localStorage.getItem('employee');
    if (employee) {
      try {
        const img = JSON.parse(employee).photo;
        this.imageempurl = atob(img);
      } catch (error) {
        this.imageempurl = "assets/default.png"; // Fallback image
      }
    }
  }

  // Extracts authorities (permission strings) from the JWT token
  getAuthorities() {
    // @ts-ignore
    const jwtToken = localStorage.getItem("Authorization").split(' ')[1];
    return jwtDecode(jwtToken).aud;
  }

  // Returns the currently loaded profile image URL
  getUserProfile(): string {
    return this.imageempurl;
  }

  // Re-initializes menu visibility states from localStorage on app reload
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

  // Clears the stored username
  clearUsername(): void {
    localStorage.removeItem(this.localStorageUsreName);
  }

  // Clears all menu states from localStorage (used during logout)
  clearMenuState(): void {
    const menus = this.getNavListItem();
    menus.forEach(menu => {
      // @ts-ignore
      localStorage.removeItem(this['localStorage' + menu.Menu + 'Menus']);
    });
  }
}
