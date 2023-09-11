import {AfterViewInit, Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import adapter from 'webrtc-adapter';


@Component({
  selector: 'app-live-stream',
  templateUrl: './live-stream.component.html',
  styleUrls: ['./live-stream.component.scss']
})
export class LiveStreamComponent implements OnInit,AfterViewInit {
  @ViewChild('displaySurface') preferredDisplaySurface;
  @ViewChild('startButton') startButton;
  @ViewChild('gumLocal') gumLocal;
  @ViewChild('options') options;
  constructor(private renderer2: Renderer2) {

  }

  ngOnInit(): void {
  }

  handleSuccess(stream) {
    this.startButton.disabled = true;
    this.preferredDisplaySurface.disabled = true;
    this.gumLocal.srcObject = stream;
    // demonstrates how to detect that the user has stopped
    // sharing the screen via the browser UI.
    this.renderer2.listen(stream.nativeElement.getVideoTracks()[0], 'ended', () => {
      this.errorMsg('The user has ended sharing the screen', null);
      this.startButton.disabled = false;
      this.preferredDisplaySurface.disabled = false;
    })
  }

  handleError(error) {
    this.errorMsg(`getDisplayMedia error: ${error.name}`, error);
  }


  errorMsg(msg, error) {
    const errorElement = document.querySelector('#errorMsg');
    errorElement.innerHTML += `<p>${msg}</p>`;
    if (typeof error !== 'undefined') {
      console.error(error);
    }
  }

  ngAfterViewInit(): void {
    if (adapter.browserDetails.browser === 'chrome' &&
      adapter.browserDetails.version >= 107) {
      // See https://developer.chrome.com/docs/web-platform/screen-sharing-controls/
      this.renderer2.setStyle(this.options.nativeElement, "style", 'block')
    } else if (adapter.browserDetails.browser === 'firefox') {
      // Polyfill in Firefox.
      // See https://blog.mozilla.org/webrtc/getdisplaymedia-now-available-in-adapter-js/
      // adapter.browserShim.shimGetDisplayMedia(window, 'screen');
    }

    this.renderer2.listen(this.startButton.nativeElement, 'click', () => {
      let options = {audio: true, video: true};
      let displaySurface = this.preferredDisplaySurface.options[this.preferredDisplaySurface.selectedIndex].value;
      if (displaySurface !== 'default') {
        options.video = displaySurface;
      }
      navigator.mediaDevices.getDisplayMedia(options)
        .then(this.handleSuccess, this.handleError);
    })

    if ((navigator.mediaDevices && 'getDisplayMedia' in navigator.mediaDevices)) {
      this.startButton.disabled = false;
    } else {
      this.errorMsg('getDisplayMedia is not supported', null);
    }
  }


}
