import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Tag} from "../shared/tag";
import {TagsService} from "./tags.service";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.sass']
})
export class TagsComponent implements OnInit, OnChanges {

  @Input()
  tags: Tag[]
  @Input()
  active_note_id:number = undefined
  private chip

  constructor(private tagsService:TagsService, @Inject('M') private M: any) { }

  ngOnInit(): void {
    const self = this
    document.addEventListener('DOMContentLoaded', function () {
      const elems = document.querySelectorAll('.chips');
      self.chip = self.M.Chips.init(elems,
        {
          placeholder: "Enter tags",
          onChipAdd: self.onAddTag.bind(self),
          onChipDelete: self.onRemoveTag.bind(self)
        })[0];
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if(this.tags !== undefined)
      this.tags.map(tag => tag.name).map(tag_name => this.chip.addChip({tag: tag_name}))
  }

  onAddTag(chips, chip){
    const self = this
    const chipText = chip.textContent.slice(0, -5)
    if(self.tags.find(tag => tag.name === chipText))
      return
    this.tagsService.available_tags
      .then(tags =>
        tags.find(tag => tag.name === chipText))
      .then(tag => {
        if(tag === undefined)
          return self.tagsService.postTag(chipText)
            .then(tag => {
              self.tags.push(tag)
              return self.tags
            })
        else {
          self.tags.push(tag)
          return self.tags
        }
      })
      .then(async tags => await self.tagsService.updateTags(
          tags.map(tag => tag.id),
          self.active_note_id)
      )
  }

  onRemoveTag(chips, chip){
    const chipText = chip.textContent.slice(0, -5)
    const tag = this.tags.find(tag => tag.name === chipText)
    if(tag === undefined)
      return
    this.tags = this.tags.filter(tag => tag.name !== chipText)
    this.tagsService.updateTags(this.tags.map(tag => tag.id),
      this.active_note_id)
  }



}
