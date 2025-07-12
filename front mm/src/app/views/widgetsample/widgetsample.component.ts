import { Component, Input, OnInit } from '@angular/core';
import { cilListNumbered, cilPaperPlane, cilBellExclamation, cilCheckCircle, cilBookmark, cilCalendar, cilCalendarCheck, cilGlobeAlt, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa,cifCa, cifBb, cifCd,cifAe ,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn, cibInstagram, cibFacebook, cibYoutube, cibLinkedin, cibTwitter, cilOptions, cilChartPie, cilHeart, cilChatBubble, cilGroup, cibCampaignMonitor, cilMonitor } from '@coreui/icons';
import { IconSetService } from '@coreui/icons-angular';
import { GlobalService } from '../GlobalService';


@Component({
  selector: 'app-widgetsample',
  templateUrl: './widgetsample.component.html',
  styleUrl: './widgetsample.component.scss',

})
export class WidgetsampleComponent implements OnInit{

  @Input() data: any; 

  selectedOption: any;
  selectedKey : string ='';
  DisplayedKey : string ='';
  DisplayedCategoryKey : string ='';

  isDropdownOpen: boolean = false; 
  likesCount = false;
  commentsCount = false;
  followersCount = false;
  count: number = 0;
  selectedCategory: any = '';
  categoryOptions: any; 
  DisplayedPlatformKey: any;
  selectedPlatform: any = '';

  constructor(public iconSet: IconSetService, public globalService: GlobalService) {
    iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook , cibCampaignMonitor ,cibInstagram,cilMonitor,cilChartPie,cilHeart,cilChatBubble,cilGroup,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 
  }

  platformOptions: { name: string, value: string, icon: string }[] = [
    { name: 'Facebook', value: 'facebook', icon: 'cibFacebook'},
    { name: 'YouTube', value: 'youtube', icon: 'cibYoutube' },
    { name: 'Twitter', value: 'twitter', icon: 'cibTwitter' },
    { name: 'Instagram', value: 'instagram', icon: 'cibInstagram' },
    { name: 'LinkedIn', value: 'linkedin', icon: 'cibLinkedin' },
    { name: 'Other', value: 'other', icon: '' },

  ];


  ngOnInit(): void {
    this.DisplayingKey();
  }
  onCheckboxChange(checkedType: 'likes_count' | 'comments_count' | 'followers_count', event: any): void {
    event.stopPropagation();
  
    if (checkedType === 'likes_count') {
      this.commentsCount = false;
      this.followersCount = false;
      if (this.likesCount === true) {this.selectedKey = 'likes_count' ;} else {this.selectedKey = '' ; this.selectedOption=null; this.DisplayedKey = '';}
      
    } else if (checkedType === 'comments_count') {
      this.likesCount = false;
      this.followersCount = false;
      if (this.commentsCount === true) {this.selectedKey = 'comments_count' ;} else {this.selectedKey = '' ; this.selectedOption=null; this.DisplayedKey = '';}
    } else if (checkedType === 'followers_count') {
      this.likesCount = false;
      this.commentsCount = false;
      if (this.followersCount === true) {this.selectedKey = 'followers_count' ;} else {this.selectedKey = '' ; this.selectedOption=null; this.DisplayedKey = '';}

    }
  }
 
  countAccounts(): number {
    let count: any;
  
    if (this.selectedKey !== '' && this.selectedOption && this.selectedOption.length === 2) {
      const [minValue, maxValue] = this.selectedOption;
      count = this.globalService.CountItems(this.data, this.selectedKey, maxValue, minValue);
      this.DisplayingKey();
    } else {
      count = this.data.length; 
    }       
  
    return count;
  }
  
  DisplayingKey()  {

    if(this.selectedOption)
   { const [minValue, maxValue] = this.selectedOption;
    if (this.selectedKey === 'likes_count' && this.selectedOption && this.selectedOption.length === 2) { this.DisplayedKey = 'Likes between '+ this.globalService.formatNumber(minValue) + ' and ' + this.globalService.formatNumber(maxValue)}
    else if (this.selectedKey === 'comments_count' && this.selectedOption && this.selectedOption.length === 2) { this.DisplayedKey = 'Comments between ' + this.globalService.formatNumber(minValue) + ' and ' + this.globalService.formatNumber(maxValue)}
    else if (this.selectedKey === 'followers_count' && this.selectedOption && this.selectedOption.length === 2) { this.DisplayedKey = 'Followers between ' + this.globalService.formatNumber(minValue) + ' and ' + this.globalService.formatNumber(maxValue)}
    else { this.DisplayedKey = '';}
  }
    else if (this.selectedCategory !== ''){ this.DisplayedCategoryKey= 'Category : '+this.selectedCategory}
    else { this.DisplayedCategoryKey= '' }

    if (this.selectedPlatform!== '') { this.DisplayedPlatformKey= 'Platform : '+this.selectedPlatform}
    else { this.DisplayedPlatformKey= '' }

  }

  CategoryOptions(): void{
    this.categoryOptions= this.globalService.getCategoryOptions(this.data); 
  }
  countAccountsByCategory(): number {
    let count: any;
    this.CategoryOptions();
    if (this.selectedCategory !== '') {
      count = this.globalService.CountItems(this.data,'category', this.selectedCategory, null);    
      
    } else {
      count = this.data.length; 
    }       
    this.DisplayingKey();
    return count;
  }
  countAccountsByPlatform(): number {
    let count: any;
    
    if (this.selectedPlatform !== '') {
      count = this.globalService.CountItems(this.data,'platform', this.selectedPlatform, null);    
      
    } else {
      count = this.data.length; 
    }       
    this.DisplayingKey();
    return count;
  }
  countAccountsByType(type:any): number {
    let count: any;
    
    if (type == 'competitor') {
      count = this.globalService.CountItems(this.data,'competitor', true , null);    
      
    } else {
      count = this.data.length; 
    }       
    return count;
  }
 
  
}
