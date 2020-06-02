import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {

  @Input()
  header_text: string

  @Output()
  onNoteClickEvent = new EventEmitter<[number, string]>()

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onNoteClick(event: [number, string]){
    this.onNoteClickEvent.emit(event)
  }

  logout(){
    this.authService.logout()
    window.location.reload()
  }
}
