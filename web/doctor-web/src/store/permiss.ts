import { defineStore } from 'pinia';

interface ObjectList {
	[key: string]: string[];
}

export const usePermissStore = defineStore('permiss', {
	state: () => {
	
		return {
			key: <string[]>[],
			defaultList: <ObjectList>{
				//管理员
				0: ['0', '1', '2', '3'],
				//老师
				1: ['0', '15'],
				//实验员
				2: ['0', '22'],
				//学生
				3: ['0', '16'],
			}
		};
	},
	actions: {
		handleSet(val: string[]) {
			this.key = val;
		},
	    setKey(){
			this.key =localStorage.getItem('permiss')?JSON.parse(localStorage.getItem('permiss')):[];
		}
	}
});
