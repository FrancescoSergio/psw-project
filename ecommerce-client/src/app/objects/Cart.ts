import { User } from "./User";
import { CartItem } from "./CartItem";

export interface Cart{
  id: number;
  user: User;
  cartItems: CartItem[];
}
