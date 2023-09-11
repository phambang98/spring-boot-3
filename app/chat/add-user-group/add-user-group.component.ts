import {AfterViewInit, Component, Inject, Injectable, Input, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {UserService} from "../../_services/user.service";
import {ChatGroupModel} from "../../_dtos/chat/ChatGroupModel";
import {UserChatGroupModel} from "../../_dtos/chat/UserChatGroupModel";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {CloseDialog} from "../../_dtos/chat/CloseDialog";

@Component({
  selector: 'nb-dialog-showcase',
  template: `
    <nb-card>
      <nb-card-header>Thêm thành viên vào nhóm {{ title }}</nb-card-header>
      <nb-card-body>
        <div class="form-group">
          <input #userName (keydown)="processKeyDown()" (keyup.enter)="keydownInput($event)" nbInput
                 placeholder="User Name" class="input-user-name" type="text">
        </div>
        <div class="col alert alert-danger" role="alert"
             *ngIf="!existsUserName && userName.value">
          Invalid Username!
        </div>
        <div class="col alert alert-danger" role="alert"
             *ngIf="duplicateItem && userName.value">
          Đã tồn tại
        </div>
        <div class="list" [ngClass]="listItems.length == 0 ? 'displayEmpty' : ''">
          <label for="html" id="{{ i }}" *ngFor="let item of listItems; let i = index"
                 (click)="delete(i)">{{ item }}
            <nb-icon icon="close-outline"></nb-icon>
          </label>
        </div>
        <div class="col alert alert-danger" role="alert"
             *ngIf="!existsListItem">
          Không có ai
        </div>
      </nb-card-body>
      <nb-card-footer class="text-center">
        <button nbButton (click)="submit()" status="primary" class="m-2">
          Submit
        </button>
        <button nbButton (click)="dismiss()" status="danger" class="m-2">Close</button>
      </nb-card-footer>
    </nb-card>
  `,
  styleUrls: ['./add-user-group.component.scss']
})

export class AddUserGroupComponent implements OnInit {

  @Input() title: string;

  constructor(protected ref: NbDialogRef<AddUserGroupComponent>, private userService: UserService) {
  }

  listItems = []


  ngOnInit() {

  }

  existsUserName: Boolean = true
  existsListItem: Boolean = true
  duplicateItem: Boolean = false

  dismiss() {
    this.ref.close();
  }

  processKeyDown() {
    this.existsUserName = true
    this.existsListItem = true
    this.duplicateItem = false
  }

  keydownInput(event) {
    if (this.listItems.includes(event.target.value) == true) {
      this.duplicateItem = true
      return
    }
    this.duplicateItem = false
    this.userService.existsByUserName(event.target.value).subscribe({
      next: (v: Boolean) => {
        if (v === true) {
          this.listItems.push(event.target.value);
        }
        this.existsUserName = v
      }, error: (e) => {
        console.log("error", e)
      }
    })
  }

  delete(item) {
    this.listItems.splice(item, 1);
  }

  submit() {
    if (this.listItems.length == 0) {
      this.existsListItem = false
    } else {
      this.ref.close(new CloseDialog(true, this.listItems));
    }
  }


}
