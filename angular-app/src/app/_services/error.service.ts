import {Injectable} from '@angular/core';
import {
  DialogAuthenticationFailureComponent
} from "../shared/dialog/dialog-alert/dialog-authentication-failure.component";
import {NbDialogService} from "@nebular/theme";
import {TokenStorageService} from "./token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor(private nbDialogService: NbDialogService,private tokenStorageService :TokenStorageService) {
  }


  public errorFetch(e: any) {
    if (e.status === 401) {
      this.nbDialogService.open(DialogAuthenticationFailureComponent, {
        context: {title: "Authentication", message: 'Session Time Out'}
      }).onClose.subscribe(() => {
        this.tokenStorageService.signOut()
        window.location.reload()
      });
    } else {
      console.log(e)
    }
  }
}
