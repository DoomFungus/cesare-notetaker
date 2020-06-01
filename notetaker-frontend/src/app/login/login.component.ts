import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {AuthService} from "../shared/auth.service";
import {EncryptionService} from "../shared/encryption.service";
import {TagsService} from "../tags/tags.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  loginLoadAnimation: HTMLElement;
  constructor(private loginService:AuthService,
              private encryptionService: EncryptionService,
              private tagService: TagsService,
              @Inject('M') private M: any) { }

  @Output()
  onLoggedInEvent = new EventEmitter<string>()

  ngOnInit(): void {
    const self = this
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.log-modal');
      const instances = self.M.Modal.init(elems, {dismissible: false});
      self.M.Modal.getInstance(document.getElementById("login-modal")).open()
      self.loginLoadAnimation = document.getElementById("login_loader")
    });
  }

  public model = {
    username: "",
    password: "",
    headingMessage: "Please enter your credentials"
  };

  onLogin(){
    const self = this;
    this.loginLoadAnimation.style.display = 'block'
    this.loginService.signIn(this.model.username, this.model.password).subscribe(
      data => self.onSuccessfulLogin(self.model.username, self.model.password, data.accessToken, data.refreshToken),
      error => self.onUnsuccessfulLogin(error)
    )
  }

  onSignup(){
    const self = this;
    this.loginLoadAnimation.style.display = 'block'
    this.loginService.signUp(this.model.username, this.model.password).subscribe(
      data => self.onSuccessfulLogin(self.model.username, self.model.password, data.accessToken, data.refreshToken),
      error => self.onUnsuccessfulSignup(error)
    )
  }

  onSuccessfulLogin(username:string, password:string, accessToken:string, refreshToken:string){
    this.onLoggedInEvent.emit(username)
    this.tagService.setAvailableTags(username)
    this.loginLoadAnimation.style.display = 'none'
    this.encryptionService.setKey(username, password)
    localStorage.setItem("access_token", accessToken)
    localStorage.setItem("refresh_token", refreshToken)
    this.M.Modal.getInstance(document.getElementById("login-modal")).close();
    this.model.headingMessage = "Please enter your credentials"
    this.model.username=""
    this.model.password=""
  }

  onUnsuccessfulLogin(error){
    this.loginLoadAnimation.style.display = 'none'
    this.model.headingMessage = "Please verify your credentials"
  }

  onUnsuccessfulSignup(error){
    this.loginLoadAnimation.style.display = 'none'
    this.model.headingMessage = "Sorry, this username is taken"
  }
}
