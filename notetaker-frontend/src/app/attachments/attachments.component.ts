import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Attachment} from "../shared/attachment";
import {AttachmentsService} from "./attachments.service";
import saveAs from 'file-saver';

@Component({
  selector: 'app-attachments',
  templateUrl: './attachments.component.html',
  styleUrls: ['./attachments.component.sass']
})
export class AttachmentsComponent implements OnInit, OnChanges{

  constructor(private attachmentsService:AttachmentsService, @Inject('M') private M: any) { }

  @Input()
  attachments: Attachment[]
  @Input()
  parent_note_id:number

  private active_attachment_id: number

  ngOnInit(): void {
    const self = this
    document.addEventListener('DOMContentLoaded', function() {
      const elems = document.querySelectorAll('.attachments-modal');
      self.M.Modal.init(elems, {});
    });
  }

  ngOnChanges(changes: SimpleChanges) {
  }

  handleAttachmentUpload(event: Event) {
    const file = (<HTMLInputElement>event.target).files[0]
    console.log(this.parent_note_id)
    const self = this
    this.attachmentsService.postAttachment(this.parent_note_id, file)
       .then(data => {
         self.attachments.push(data)
       });
  }

  handleAttachmentDownload(attachment_id:number, attachment_name: string){
    this.attachmentsService.getAttachmentContent(attachment_id.toString())
      .then(data => saveAs(data, attachment_name)
      )
  }

  openDeleteAttachmentModal(attachment_id:number){
    this.active_attachment_id = attachment_id
    this.M.Modal.getInstance(document.getElementById("delete-attachment-modal")).open()
  }

  onDeleteAttachmentFinish(){
    const self = this
    this.attachmentsService.deleteAttachment(this.active_attachment_id).subscribe(
      data => {
        self.attachments = self.attachments
          .filter(element => {
            return element.id !== self.active_attachment_id
          })
        self.M.Modal.getInstance(document.getElementById("delete-attachment-modal")).close()
      }
    )
  }
}
