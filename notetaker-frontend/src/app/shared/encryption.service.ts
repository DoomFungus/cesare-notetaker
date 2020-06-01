import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  private enc = new TextEncoder();
  private dec = new TextDecoder();
  private key: Promise<CryptoKey>;
  constructor() {
  }

  private getKeyMaterial(password) {
    return window.crypto.subtle.importKey(
      "raw",
      this.enc.encode(password),
      "PBKDF2",
      false,
      ["deriveBits", "deriveKey"]
    );
  }

  private getSalt(raw:string): Uint8Array{
    return this.enc.encode(raw)
  }

  private async deriveKey(username:string, password:string): Promise<CryptoKey>{
    let keyMaterial = await this.getKeyMaterial(password);
    let key = window.crypto.subtle.deriveKey(
      {
        "name": "PBKDF2",
        salt: this.getSalt(username),
        "iterations": 100000,
        "hash": "SHA-256"
      },
      keyMaterial,
      { "name": "AES-GCM", "length": 128},
      true,
      [ "encrypt", "decrypt" ]
    );
    return key
  }

  public async setKey(username: string, password:string):Promise<void>{
    const self = this
    this.key = this.deriveKey(username, password);
  }

  public async encrypt(plaintext: string) : Promise<string>{
    const iv = window.crypto.getRandomValues(new Uint8Array(12));
    const encText = this.enc.encode(plaintext)
    const key:CryptoKey = await this.key
    return window.crypto.subtle.encrypt(
      {
        name: "AES-GCM",
        iv: iv
      },
      key,
      encText)
      .then(data => this.addHeader(iv, data))
      .then(data => this.arrayToBase64(data))
  }

  public async encryptBinary(plaintext: ArrayBuffer) : Promise<string>{
    const iv = window.crypto.getRandomValues(new Uint8Array(12));
    const key:CryptoKey = await this.key
    return window.crypto.subtle.encrypt(
      {
        name: "AES-GCM",
        iv: iv
      },
      key,
      plaintext)
      .then(data => this.addHeader(iv, data))
      .then(data => this.arrayToBase64(data))
  }

  public async decrypt(ciphertext: string) : Promise<string>{
    const encCiphertext = this.base64ToArray(ciphertext)
    const [header, body] = this.splitHeader(encCiphertext, 12)
    const key:CryptoKey = await this.key
    return window.crypto.subtle.decrypt(
      {
        name: "AES-GCM",
        iv: header
      },
      key,
      body)
    .then(data => this.dec.decode(data))
  }

  public async decryptBinary(ciphertext: string) : Promise<ArrayBuffer>{
    const encCiphertext = this.base64ToArray(ciphertext)
    const [header, body] = this.splitHeader(encCiphertext, 12)
    const key:CryptoKey = await this.key
    return window.crypto.subtle.decrypt(
      {
        name: "AES-GCM",
        iv: header
      },
      key,
      body)
  }

  public async encodeText(text: string) : Promise<Uint8Array>{
    const base64text = atob(text)
    return this.enc.encode(text)
  }

  public async decodeText(text: Uint8Array) : Promise<string>{
    return btoa(this.dec.decode(text))
  }

  private addHeader(header: Uint8Array, data: ArrayBuffer): Uint8Array{
    const dataArray = new Uint8Array(data)
    let result = new Uint8Array(header.length + dataArray.length)
    result.set(header)
    result.set(dataArray, header.length)
    return result
  }

  private splitHeader(data: ArrayBuffer, offset: number): [Uint8Array, Uint8Array]{
    const dataArray = new Uint8Array(data)
    return [
      dataArray.subarray(0, offset),
      dataArray.subarray(offset)
    ]
  }

  private arrayToBase64(arrayBuffer: Uint8Array):string {
    const byteArray = new Uint8Array(arrayBuffer);
    let byteString = '';
    for(let i=0; i < byteArray.byteLength; i++) {
      byteString += String.fromCharCode(byteArray[i]);
    }
    return window.btoa(byteString);
  }

  private base64ToArray(b64: string): Uint8Array {
    const byteString = window.atob(b64);
    const byteArray = new Uint8Array(byteString.length);
    for(let i=0; i < byteString.length; i++) {
      byteArray[i] = byteString.charCodeAt(i);
    }
    return byteArray;
  }
}
