import { Router } from '@angular/router';
import { AuthService } from './../auth/shared/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean;
  username: string;
  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    // this.isLoggedIn = this.authService.isLoggedIn();
    // this.username = this.authService.getUsername();
    this.authService.loggedIn.subscribe(data => {
      this.isLoggedIn = data;
    });
    this.authService.username.subscribe(data => {
      this.username = data;
    });
  }

  goToUserProfile() {
    this.router.navigateByUrl(`/user-profile/${this.username}`);
  }

  logout() {
    this.authService.logout().subscribe(data => {
      // clear token details
      this.authService.clearTokenDetails();
      this.authService.loggedIn.emit(false);
      this.authService.username.emit('');
      this.router.navigateByUrl('/login');
    });
  }
}
