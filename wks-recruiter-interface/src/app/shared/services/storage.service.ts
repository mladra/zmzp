import { Injectable } from '@angular/core';

@Injectable()
export class StorageService {

    private itemName = 'data';

    public constructor() { }

    addDataToStorage(data: string) {
        localStorage.setItem(this.itemName, data);
    }

    getDataFromStorage() {
        return localStorage.getItem(this.itemName);
    }

    clearStorage() {
        localStorage.removeItem(this.itemName);
    }
}
