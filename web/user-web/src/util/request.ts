import axios, { type AxiosResponse } from "axios";

class ApiService {
    private instance = axios.create({
        baseURL: "http://localhost:9092/api",
        timeout: 5000,
    });

    constructor() {
        this.instance.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem("userToken");
                console.log("Token: " + token);
                if (token) {
                    config.headers.Authorization = `Bearer ${token}`;
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        this.instance.interceptors.response.use(
            (response) => {
                console.log(JSON.stringify(response.data));
                return response;
            },
            (error) => {
                return Promise.reject(error);
            }
        );
    }

    mapToResponse<T>(res: AxiosResponse): Response<T> {
        return new Response<T>(res.data.code, res.data.message, res.data.data);
    }

    async get<T>(url: string): Promise<Response<T>> {
        return this.instance.get(url).then((res) => this.mapToResponse(res));
    }

    async post<T>(url: string, data: any): Promise<Response<T>> {
        return this.instance.post(url, data).then((res) => this.mapToResponse(res));
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
