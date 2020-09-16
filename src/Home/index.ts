import module from '../module';

export interface getHome {
  id?: string;
  image?: string;
  title?: string;
  chapter_id?: string;
  chapter_title?: string;
  view?: number;
  time?: string;
}

export const getHome = (): Promise<getHome[]> => {
  return module.getHome();
};
