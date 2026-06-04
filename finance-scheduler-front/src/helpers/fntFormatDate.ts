export default function fntFormatDate (value: string) {
	const date = new Date(value);

	if (isNaN(date.getTime())) {
		return 'Data inválida'; 
	}

	return date.toLocaleDateString('pt-BR', {
		timeZone: 'UTC'
	});
}