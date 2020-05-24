import { Component } from '@angular/core';
import * as ClassicEditor from 'cesare-notetaker-editor-build';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'notetaker-frontend';
  public Editor = ClassicEditor;
  ckConfig = {
    image: {
      styles: ['full', 'alignLeft', 'alignRight'],
      toolbar: [ 'imageStyle:full', 'imageStyle:alignRight', 'imageStyle:alignLeft' ]
    }
  };
}
