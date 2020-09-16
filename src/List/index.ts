import module from '../module';

export const ListLimit = 156;

export interface getByList {
  id: string;
  title: string;
}

export interface getByListWithImage {
  page?: number;
  sort?: 'name' | 'views' | 'last_update';
  sort_type?: 'ASC' | 'DESC';
}

export interface getByListWithImageResult {
  id?: string;
  image?: string;
  title?: string;
  view?: number;
  chapter?: string;
}

export const getByList = async (): Promise<getByList[]> => {
  return module.getByList();
};

export const getByListWithImage = async ({
  page = 1,
  sort = 'last_update',
  sort_type = 'DESC',
}: getByListWithImage): Promise<getByListWithImageResult[]> => {
  if (page > ListLimit) {
    throw 'limit';
  } else {
    return module.getList(
      `https://loveheaven.net/manga-list.html?listType=pagination&page=${page}&sort=${sort}&sort_type=${sort_type}`
    );
  }
};
