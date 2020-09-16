import module from '../module';

export interface getPostView {
  next?: string;
  prev?: string;
  content?: string[];
}

export interface getPosts {
  image?: string;
  title?: string;
  title_jp?: string;
  author?: string;
  genre?: Array<{
    id: string;
    title: string;
  }>;
  status?: string;
  magazine?: string;
  view?: number;
  description?: string;
  list?: Array<{
    id: string;
    title?: string;
    time?: string;
  }>;
}

export const getPosts = async (url: string): Promise<getPosts> => {
  return module.getPosts(url);
};

export const getPostView = async (url: string): Promise<getPostView> => {
  return module.getPostView(url);
};
