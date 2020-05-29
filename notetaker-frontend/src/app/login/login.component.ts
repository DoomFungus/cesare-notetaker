import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {NavigationService} from "../navigation/navigation.service";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  constructor(private loginService:AuthService, @Inject('M') private M: any) { }

  @Output()
  onLoggedInEvent = new EventEmitter<string>()

  ngOnInit(): void {
    const self = this
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.log-modal');
      const instances = self.M.Modal.init(elems, {dismissible: false});
      self.M.Modal.getInstance(document.getElementById("login-modal")).open()
    });
  }

  public model = {
    username: "",
    password: "",
    headingMessage: "Please enter your credentials"
  };

  onLogin(){
    const self = this;
    this.loginService.signIn(this.model.username, this.model.password).subscribe(
      data => self.onSuccessfulLogin(self.model.username, data.accessToken, data.refreshToken),
      error => self.onUnsuccessfulLogin(error)
    )
  }

  onSignup(){
    const self = this;
    this.loginService.signUp(this.model.username, this.model.password).subscribe(
      data => self.onSuccessfulLogin(self.model.username, data.accessToken, data.refreshToken),
      error => self.onUnsuccessfulSignup(error)
    )
  }

  onSuccessfulLogin(username:string, accessToken:string, refreshToken:string){
    this.onLoggedInEvent.emit(this.model.username)
    const accessExpiresAt = JSON.parse(atob(accessToken.split('.')[1])).exp * 1000
    const refreshExpiresAt = JSON.parse(atob(refreshToken.split('.')[1])).exp * 1000
    localStorage.setItem("access_token", accessToken)
    localStorage.setItem("access_expires_at", accessExpiresAt.toString())
    localStorage.setItem("refresh_token", refreshToken)
    localStorage.setItem("refresh_expires_at", refreshExpiresAt.toString())
    this.M.Modal.getInstance(document.getElementById("login-modal")).close();
    this.model.headingMessage = "Please enter your credentials"
  }

  onUnsuccessfulLogin(error){
    this.model.headingMessage = "Please verify your credentials"
  }

  onUnsuccessfulSignup(error){
    this.model.headingMessage = "Sorry, this username is taken"
  }
}
