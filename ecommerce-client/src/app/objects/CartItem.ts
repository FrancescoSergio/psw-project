import { Cart } from './Cart';
import { Product } from './Product';

export interface CartItem{
  id?: number;
  product: Product;
  quantity: number;
  cart: Cart;
}
