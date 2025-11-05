from Cryptodome.PublicKey import RSA
from Cryptodome.Signature import pkcs1_15
from Cryptodome.Hash import SHA256
import binascii

# Generate 1024-bit RSA key pair (private + public key)
key_pair = RSA.generate(bits=1024)
public_key = key_pair.publickey()

# ---- SIGNING ----
message = b"Message for RSA signing"
hash_obj = SHA256.new(message)
signer = pkcs1_15.new(key_pair)
signature = signer.sign(hash_obj)
print("Signature:", binascii.hexlify(signature))

# ---- VERIFY VALID SIGNATURE ----
valid_hash = SHA256.new(message)
verifier = pkcs1_15.new(public_key)
try:
    verifier.verify(valid_hash, signature)
    print(f"Original message: {message}")
    print(f"{message} - Signature is valid.")
except (ValueError, TypeError):
    print(f"{message} - Signature is invalid.")

# ---- VERIFY TAMPERED MESSAGE ----
tampered_message = b"A tampered message"
tampered_hash = SHA256.new(tampered_message)
verifier = pkcs1_15.new(public_key)
try:
    verifier.verify(tampered_hash, signature)
    print(f"{tampered_message} - Signature is valid.")
except (ValueError, TypeError):
    print(f"{tampered_message} - Signature is invalid.")
