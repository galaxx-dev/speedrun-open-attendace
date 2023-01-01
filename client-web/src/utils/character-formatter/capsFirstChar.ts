/**
 * Capitalize only the first character of a string.
 *
 * @param str string to be caps the first letter
 * @returns string with capitalized first char
 */
const capsFirstChar = (str: string): string => {
  if (!str) return str

  return str.charAt(0).toUpperCase() + str.slice(1)
}

export default capsFirstChar
