import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'Cesare Notetaker';
  active_note_id: number = 0
  active_note_title: string = "Choose note"
  username: string

  onNoteChosen(event: [number, string]){
    this.active_note_id = event[0]
    this.active_note_title = event[1]
    document.getElementById("editor").style.display = "inline"
  }

  onLoggedIn(event: string){
    this.username = event
  }
}
