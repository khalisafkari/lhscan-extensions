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
  chapter_id?: string;
  chapter_title?: string;
  genre?: Array<{
    id: string;
    title: string;
  }>;
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

interface configList {
  page?: number;
  artist?: string;
  author?: string;
  group?: string;
  m_status?: '' | 1 | 2 | 3;
  search?: string;
  genre?: string;
  ungenre?: string;
  sort?: 'views' | 'name' | 'last_update';
  sort_type?: 'DESC' | 'ASC';
}

export interface postList {
  page: any;
  list: getByListWithImageResult[];
}

//https://loveheaven.net/manga-list.html?listType=pagination&page=1&artist=&author=&group=&m_status=&name=&genre=&ungenre=&sort=views&sort_type=DESC
export const postList = async ({
  page = 1,
  artist = '',
  author = '',
  group = '',
  m_status = '',
  search = '',
  genre = '',
  ungenre = '',
  sort = 'views',
  sort_type = 'DESC',
}: configList): Promise<postList> => {
  try {
    const url: string = `https://loveheaven.net/manga-list.html?listType=pagination&page=${page}&artist=${artist}&author=${author}&group=${group}&m_status=${m_status}&name=${search}&genre=${genre}&ungenre=${ungenre}&sort=${sort}&sort_type=${sort_type}`;
    const { page: p, list }: postList = await module.postList(url);
    return {
      page: Number(p.match(/\d+/g)[1]),
      list,
    };
  } catch (e) {
    throw new Error(e);
  }
};
