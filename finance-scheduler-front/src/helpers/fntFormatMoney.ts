export default function fntFormatMoney (value: number) {
	return value.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});
}