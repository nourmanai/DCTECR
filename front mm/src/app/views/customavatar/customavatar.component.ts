import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-customavatar',
  templateUrl: './customavatar.component.html',
  styleUrls: ['./customavatar.component.scss']
})
export class CustomAvatarComponent implements OnInit {
  @Input() user: any; // Input property for user object
  avatarUrl: string = './assets/img/avatars/10.jpg'; // Default avatar URL

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Load avatar URL when the component initializes
    this.loadAvatarUrl();
  }

  public async loadAvatarUrl(): Promise<void> {
    const imageUrl = this.getInfo('profile_image_url', this.user);

    if (imageUrl) {
      try {
        const response = await this.http.head(imageUrl, { observe: 'response' }).toPromise();
        if (response?.status === 200) {
          this.avatarUrl = imageUrl; // Update avatar URL if image is valid
        }
      } catch (error) {
        console.error('Failed to load image:', error);
        this.handleAvatarError(error);
      }
    }
  }

  getInfo(property: string, data: any): any {
    return data ? data.fields[property] : undefined;
  }

  handleAvatarError(error: any): void {
    console.error('Avatar loading error:', error);
    // Handle avatar loading error here (e.g., set default avatar URL)
    this.avatarUrl = './assets/img/avatars/10.jpg';
  }
}
