import {Component, Inject, OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  constructor( @Inject('M') private M: any) { }

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
    password: ""
  };

  onLogin(){
    console.log(this.model.username, this.model.password)
  }
}
