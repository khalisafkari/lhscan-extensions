import module from '../module';

export interface getByGenre {
  id?: string;
  title?: string;
  des?: string;
}

export interface getPostsByGenre {
  genre?: string;
  page?: number;
  sort?: 'name' | 'views' | 'last_update';
  sort_type?: 'ASC' | 'DESC';
}

export interface getPostsByGenreResult {
  id?: string;
  image?: string;
  title?: string;
  view?: number;
  chapter?: string;
}

export const getByGenre = async (): Promise<getByGenre[]> => {
  return module.getByGenre();
};

export const getPostsByGenre = async ({
  genre = 'action',
  page = 1,
  sort = 'last_update',
  sort_type = 'DESC',
}: getPostsByGenre): Promise<getPostsByGenreResult[]> => {
  return module.getList(
    `https://loveheaven.net/manga-list.html?listType=pagination&page=${page}&genre=${genre}&sort=${sort}&sort_type=${sort_type}`
  );
};
