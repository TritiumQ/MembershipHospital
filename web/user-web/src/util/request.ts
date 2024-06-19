import axios, { type AxiosResponse } from "axios";
/**
 * 统一封装请求
 */
class ApiService {
    private instance = axios.create({
        baseURL: "http://localhost:9092/api",
        timeout: 5000,
    });

    constructor() {
        this.instance.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem("userToken");
                //console.log("Token: " + token);
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );
    }

    mapToResponse<T>(res: AxiosResponse, fullLogging: boolean): Response<T> {
        if (fullLogging) {
            console.log(`Response: ${JSON.stringify(res)}`);
        }
        else {
            console.log(`Response: ${JSON.stringify(res.data).substring(0, 100)}... (data too long)`);
        }
        return new Response<T>(res.data.code, res.data.message, res.data.data);
    }

    async get<T>(url: string, fullLogging: boolean = false): Promise<Response<T>> {
        console.log(`GET ${url}`);
        return this.instance.get(url).then((res) => this.mapToResponse(res, fullLogging));
    }

    async post<T>(url: string, data: any, fullLogging: boolean = false): Promise<Response<T>> {
        if (fullLogging) {
            console.log(`POST ${url} with data: ${JSON.stringify(data)}`);
        }
        else {
            console.log(`POST ${url} with data: ${JSON.stringify(data).substring(0, 100)}... (data too long)`);
        }
        return this.instance.post(url, data).then((res) => this.mapToResponse(res, fullLogging));
    }


    axios() {
        return this.instance;
    }
};

export class Response<T> {
    code: number;
    message: string;
    data: T;
    constructor(code: number, message: string, data: T) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    isSuccess(): boolean {
        return this.code === 114514;  
    }
}

export const apiService = new ApiService();
