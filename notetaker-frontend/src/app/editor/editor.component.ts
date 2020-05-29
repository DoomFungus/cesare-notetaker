import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import * as ClassicEditor from 'cesare-notetaker-editor-build';
import {EditorService} from "./editor.service";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.sass']
})
export class EditorComponent implements OnInit, OnChanges{

  @Input()
  note_id:number

  public model = {
    editorData: ""
  };

  constructor(private editorService:EditorService) { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const self = this;
    if (this.note_id !== undefined){
      document.getElementById("content_loader").style.display = 'block'
      this.editorService
        .getNoteContent(this.note_id.toString())
        .subscribe(value => {
          value.text()
            .then(text => self.model.editorData = text)
          document.getElementById("content_loader").style.display = 'none'
        })
    }
  }

  public Editor = ClassicEditor;
  ckConfig = {
    image: {
      styles: ['full', 'alignLeft', 'alignRight'],
      toolbar: [ 'imageStyle:full', 'imageStyle:alignRight', 'imageStyle:alignLeft' ]
    }
  };

  onSave(){
    this.editorService.putNoteContent(this.note_id.toString(), this.model.editorData)
      .subscribe();
  }
}
