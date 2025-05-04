export const parseObject = (data: string) => {
  try {
    return JSON.parse(data);
  } catch (e: any) {
    return e.message;
  }
};
