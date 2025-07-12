import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GetImgService } from './../GetImgService';
import { IconSetService } from '@coreui/icons-angular';
import { cilListNumbered, cilPaperPlane, cilBellExclamation, cilCheckCircle, cilBookmark, cilCalendar, cilCalendarCheck, cilGlobeAlt, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa,cifCa, cifBb, cifCd,cifAe ,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn, cibInstagram, cibFacebook, cibYoutube, cibLinkedin, cibTwitter, cilOptions, cilChatBubble, cilHeart, cilGroup, cilNotes, cilLocationPin } from '@coreui/icons';
import * as iso3166 from 'iso-3166-1-alpha-2';
import { iconSubset } from 'src/app/icons/icon-subset';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent implements OnInit {

  Url: string = '';
  user: any;

  cilMagnifyingGlass = iconSubset.cilMagnifyingGlass;

  constructor( private http: HttpClient,  @Inject(MAT_DIALOG_DATA) public data: any, private ImgService: GetImgService, public iconSet: IconSetService)
  {
    iconSet.icons = {cibTwitter,cibLinkedin,cibYoutube,cibFacebook ,cibInstagram,cilHeart,cilGroup,cilLocationPin,cilNotes,cilChatBubble,cilOptions,cilCheckCircle, cilGlobeAlt, cilListNumbered, cilPaperPlane, cilBellExclamation, cilBookmark, cilCalendar , cilCalendarCheck, cilUser, cilWarning, cifUs, cifAd, cifBr, cifBs, cifBa, cifCa, cifBb,cifCd, cifAe,cifCh, cifCi, cifBj, cifCl,cifCm,cifCo,cifCr,cifAt,cifCu,cifCv, cifBw,cifCy,cifCz,cifDe,cifDj,cifDk,cifDm,cifDo,cifDz,cifEc,cifEe,cifEg,cifEr,cifEs,cifEt,cifFi,cifFj,cifFm,cifFr,cifId,cifIe,cifIl,cifIn,cifIq,cifIr,cifIs,cifIt,cifJm,cifJo,cifJp,cifKe,cifKg,cifKh,cifKi,cifKm,cifKn,cifKp,cifKr,cifKw,cifKz,cifGa,cifGb,cifGd,cifGe,cifGh,cifGm,cifGn,cifGq,cifGr,cifGt,cifGw,cifGy,cifHk,cifHn,cifHr,cifHt,cifHu,cifLa,cifLb,cifLc,cifLi,cifLk,cifLr,cifLs,cifLt, cifLu,cifLv,cifLy,cifMa,cifMc,cifMd,cifMe,cifMg,cifMh,cifMk,cifMl,cifMm,cifMn,cifMr,cifMt,cifMu,cifMv,cifMw,cifMx,cifMy,cifMz,cifNa,cifNe,cifNg,cifNi,cifNl,cifNo,cifNp,cifNr,cifNu,cifNz,cifOm,cifPa,cifPe,cifPg,cifPh,cifPk,cifPl,cifPt,cifPw,cifPy,cifQa,cifRo,cifRs,cifRu,cifRw,cifSa,cifSb,cifSc,cifSd,cifSe,cifSg,cifSi,cifSk,cifSl,cifSm,cifSn,cifSo,cifSr,cifSs,cifSt,cifSv,cifSy,cifSz,cifTd,cifTg,cifTh,cifTj,cifTl,cifTm,cifTn,cifTo,cifTr,cifTt,cifTv,cifTw,cifTz,cifUa,cifUg,cifUy,cifUz,cifVa,cifVc,cifVe,cifVn,cifWs,cifXk,cifYe,cifZa,cifZm,cifZw,cifAf,cifAg,cifAl,cifAm,cifAo,cifAr,cifAu,cifAz,cifBd,cifBe,cifBf,cifBg,cifBh,cifBi,cifBn,cifBo,cifBt,cifBy,cifBz,cifCf,cifCg,cifCn  }; 

  }

  ngOnInit(): void {
     // Assuming 'user' is the input to the component
     this.user = this.data.user;
     this.ImgService.loadAvatarUrl(this.user).then(url => {
      this.Url = url;
    });
  }
  getVerifiedStatus(user: any): string {
    // console.log("verified : ", this.getInfo('verified', user));
    return this.getInfo('verified', user) ? 'cilCheckCircle' : '';
  }

  countryNameMappings: { [key: string]: string } = {
    'brasil': 'Brazil',
    'españa': 'Spain'
  };


  removeSpecialCharacters(str: string): string {
    const accentedChars = /[À-ÿ]/g;
    const nonAccentedChars = str.replace(accentedChars, function(char) {
      const nonAccentedChar = char.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
      return nonAccentedChar;
    });
    return nonAccentedChars.replace(/[^a-zA-Z0-9\s]/g, '');
  }
  standardizeCountryName(country: string): string {
    const standardCountryName = (this.countryNameMappings[country.toLowerCase()]) || country;
    return standardCountryName;
  }
  //cilGlobeAlt
   loadCountries(location: any): string {
    const standardCountryName = this.standardizeCountryName(location);
    const countryCode = iso3166.getCode(this.removeSpecialCharacters(standardCountryName));
    if (countryCode && countryCode.length >= 2) {
      return countryCode.toUpperCase();
    } else {
      return ''; 
    }
  }
  getFlagIcon(countryCode: string): string {
    return `cif-${countryCode.toLowerCase()}`;
  }
  

  formatNumber(value: number): string {
    if (Math.abs(value) >= 1000000) {
      return (value / 1000000).toFixed(1) + 'M';
    } else if (Math.abs(value) >= 1000) {
      return (value / 1000).toFixed(1) + 'K';
    } else {
      return value.toFixed(0); 
    }
  } 
  getInfo(property: string, data: any): any {
    // console.log("get info ");
    return data ? data.fields[property] : undefined;
  }


}
